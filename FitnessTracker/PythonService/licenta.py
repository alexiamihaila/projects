import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from butterworth_filter import *
from sklearn.cluster import KMeans
from mpl_toolkits.mplot3d import Axes3D
from sklearn.model_selection import train_test_split
from fwd_selection import ClassificationAlgorithms
from sklearn.metrics import accuracy_score, confusion_matrix
from joblib import dump
from glob import glob
import itertools

# Load dataset
#df = pd.read_csv(r"C:\Users\alexi\IdeaProjects\LicentaConsumer\Measurements_1714127497745.csv")

#Read all CSV files in folder
# Use glob to find all CSV files in the directory
files = glob(r"C:\Users\alexi\IdeaProjects\LicentaConsumer\*.csv")
len(files)

# List to hold individual DataFrames
dfs = []

# Read each file starting from the second row and append to the list
for file in files:
    df = pd.read_csv(file, skiprows=1, header=None)
    dfs.append(df)

# Concatenate all DataFrames into one
df = pd.concat(dfs, ignore_index=True)

# Read the header from the first file
header = pd.read_csv(files[0], nrows=0).columns

# Assign the header to the concatenated DataFrame
df.columns = header

#Remove null values, empty spaces from column names and 
#remove blank spaces from end and beginning of cell
df = df.dropna()
df.columns = df.columns.str.replace(' ', '')
df = df.applymap(lambda x: x.strip() if isinstance(x, str) else x)

df['Timestamp'] = pd.to_datetime(df['Timestamp'])

df.index =  pd.to_datetime(df['Timestamp'])


##Data vizualization
# Ensure the index is reset
df.reset_index(drop=True, inplace=True)

# Get unique exercise types
exercise_types = df['Exercise_Type'].unique()

# Create a plot for each exercise type
for exercise in exercise_types:
    exercise_data = df[df['Exercise_Type'] == exercise].reset_index(drop=True)

    plt.figure(figsize=(14, 8))

    plt.plot(exercise_data.index, exercise_data['X_accelerom'], label='X_accelerom', color='r')
    plt.plot(exercise_data.index, exercise_data['Y_accelerom'], label='Y_accelerom', color='g')
    plt.plot(exercise_data.index, exercise_data['Z_accelerom'], label='Z_accelerom', color='b')

    plt.xlabel('Sample Index')
    plt.ylabel('Acceleration')
    plt.title(f'Accelerometer Data for {exercise}')
    plt.legend()

    plt.tight_layout()
    plt.show()
    
#Lowpass filtering#####################################
sf = 1000/200 #sampling frequency = 5 measurements/sec
cutoff_freq = 1.3
order = 5
col = "Y_accelerom"

lowpass_df = lowpass_filter(df, cutoff_freq, sf, order, col)

subset = lowpass_df[lowpass_df["Exercise_Type"] == "Shoulder Press"]

fig, ax = plt.subplots(nrows=2, sharex=True, figsize=(20,10))
ax[0].plot(subset["Y_accelerom"].reset_index(drop=True), label="Raw Data")
ax[1].plot(subset["Y_accelerom_lowpass"].reset_index(drop=True), label="Butterworth Filter")
ax[0].legend(loc="upper center", bbox_to_anchor=(0.5,1.15), fancybox=True, shadow=True)
ax[1].legend(loc="upper center", bbox_to_anchor=(0.5,1.15), fancybox=True, shadow=True)

columns = list(df.columns[:3])
for col in columns:
    lowpass_df = lowpass_filter(lowpass_df, cutoff_freq, sf, order, col)
    lowpass_df[col] = lowpass_df[col + "_lowpass"]
    del lowpass_df[col + "_lowpass"]
    

#Sum of squares (sqrt(x^2+y^2+x^2))###################

squares_df = lowpass_df.copy()

acc_r = np.sqrt(np.power(squares_df['X_accelerom'],2) +  np.power(squares_df['Y_accelerom'],2) + np.power(squares_df['Z_accelerom'],2)).to_numpy()

squares_df["acc_r"] = acc_r

#Mean and std deviation########

df_deviation = squares_df.copy()
columns = columns + ["acc_r"]

temporal_list = []
for et in df["Exercise_Type"].unique():
    subset = df_deviation[df_deviation["Exercise_Type"] == et].copy()
    for col in columns:
        subset[col + "_temp_mean"] = (subset[col].rolling(6).apply(np.mean))
        subset[col + "_temp_std"] = (subset[col].rolling(6).apply(np.std))
    temporal_list.append(subset)
    
df_deviation = pd.concat(temporal_list)

df_deviation.info()
subset[["Y_accelerom", "Y_accelerom_temp_mean", "Y_accelerom_temp_std"]].plot()

df_deviation = df_deviation.dropna()
df_deviation.info()

#Kmean clustering#######
df_cluster = df_deviation.copy()

cluster_columns = ["X_accelerom", "Y_accelerom", "Z_accelerom"]
inertias = []
subset = df_cluster[cluster_columns]

for k in range(2,12):
    kmeans = KMeans(n_clusters=k, n_init = 15, random_state=0)
    cluster_labes = kmeans.fit_predict(subset)
    inertias.append(kmeans.inertia_)
    
plt.figure(figsize=(10,10))
plt.plot(range(2,12), inertias, marker='o')
plt.title('Elbow method')
plt.xlabel('Number of clusters')
plt.ylabel('Inertia')
plt.show()


kmeans = KMeans(5, n_init = 15, random_state=0)
subset = df_cluster[cluster_columns]
df_cluster["cluster"] = kmeans.fit_predict(subset)
cluster_labels = kmeans.fit_predict(subset)

# Plot the clusters in 3D
fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(111, projection='3d')
# Iterate over each cluster label and plot the points belonging to that cluster
for i in range(5):
    cluster_data = subset[cluster_labels == i]
    ax.scatter(cluster_data["X_accelerom"], cluster_data["Y_accelerom"], cluster_data["Z_accelerom"], label=f'Cluster {i}')

ax.set_title('Clusters of Accelerometer Data')
ax.set_xlabel('X_accelerom')
ax.set_ylabel('Y_accelerom')
ax.set_zlabel('Z_accelerom')
ax.legend()

plt.show()


#Creating training and testing dataset 
df_train = df_cluster.copy()
X = df_train.drop(["Timestamp", "Exercise_Type"], axis = 1)
y = df_train["Exercise_Type"]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y)


# Counting the number of samples in each set
train_count = len(X_train)
test_count = len(X_test)

plt.figure(figsize=(8, 6))
plt.bar(['Train Set', 'Test Set'], [train_count, test_count], color=['green', 'orange'])
plt.xlabel('Data Set')
plt.ylabel('Number of Samples')
plt.title('Distribution of Samples in Train and Test Sets')
plt.show()



##Split feature subsets
basic_features = ["X_accelerom", "Y_accelerom", "Z_accelerom"]
square_features = ["acc_r"]
time_features = [f for f in df_train.columns if "_temp_" in f]
cluster_features = ["cluster"]

feature_set_1 = list(set(basic_features))
feature_set_2 = list(set(basic_features + square_features))
feature_set_3 = list(set(feature_set_2 + time_features))
feature_set_4 = list(set(feature_set_3 + cluster_features))

#Perform forward feature selection
learner = ClassificationAlgorithms()

max_features = 10
selected_features, ordered_features, ordered_scores = learner.forward_selection(
    max_features, X_train, y_train
)

selected_features = ['Z_accelerom_temp_mean',
 'X_accelerom_temp_mean',
 'Y_accelerom_temp_mean',
 'X_accelerom_temp_std',
 'Z_accelerom_temp_std',
 'acc_r',
 'Y_accelerom_temp_std',
 'cluster',
 'Y_accelerom',
 'X_accelerom']


plt.figure(figsize=(10,5))
plt.plot(np.arange(1,max_features + 1, 1), ordered_scores)
plt.xlabel("Number of features")
plt.ylabel("Accuracy")
plt.xticks(np.arange(1, max_features + 1, 1))
plt.show


#Performing grid search 

possible_feature_sets =[
    feature_set_1,
    feature_set_2,
    feature_set_3,
    feature_set_4,
    selected_features
]


feature_names =[
    "Feature Set 1",
    "Feature Set 2",
    "Feature Set 3",
    "Feature Set 4",
    "Selected Features"
]

#grid search
iterations = 3
score_df = pd.DataFrame()

for i, f in zip(range(len(possible_feature_sets)), feature_names):
    print("Feature set:", i)
    selected_train_X = X_train[possible_feature_sets[i]]
    selected_test_X = X_test[possible_feature_sets[i]]

    # First run non deterministic classifiers to average their score.
    performance_test_nn = 0
    performance_test_rf = 0

    for it in range(0, iterations):
        print("\tTraining neural network,", it)
        (
            class_train_y,
            class_test_y,
            class_train_prob_y,
            class_test_prob_y,
            trained_model
        ) = learner.feedforward_neural_network(
            selected_train_X,
            y_train,
            selected_test_X,
            gridsearch=False,
        )
        performance_test_nn += accuracy_score(y_test, class_test_y)

        print("\tTraining random forest,", it)
        (
            class_train_y,
            class_test_y,
            class_train_prob_y,
            class_test_prob_y,
        ) = learner.random_forest(
            selected_train_X, y_train, selected_test_X, gridsearch=True
        )
        performance_test_rf += accuracy_score(y_test, class_test_y)

    performance_test_nn = performance_test_nn / iterations
    performance_test_rf = performance_test_rf / iterations

    # And we run our deterministic classifiers:
    print("\tTraining KNN")
    (
        class_train_y,
        class_test_y,
        class_train_prob_y,
        class_test_prob_y,
    ) = learner.k_nearest_neighbor(
        selected_train_X, y_train, selected_test_X, gridsearch=True
    )
    performance_test_knn = accuracy_score(y_test, class_test_y)

    print("\tTraining decision tree")
    (
        class_train_y,
        class_test_y,
        class_train_prob_y,
        class_test_prob_y,
    ) = learner.decision_tree(
        selected_train_X, y_train, selected_test_X, gridsearch=True
    )
    performance_test_dt = accuracy_score(y_test, class_test_y)

    print("\tTraining naive bayes")
    (
        class_train_y,
        class_test_y,
        class_train_prob_y,
        class_test_prob_y,
    ) = learner.naive_bayes(selected_train_X, y_train, selected_test_X)

    performance_test_nb = accuracy_score(y_test, class_test_y)

    # Save results to dataframe
    models = ["NN", "RF", "KNN", "DT", "NB"]
    new_scores = pd.DataFrame(
        {
            "model": models,
            "feature_set": f,
            "accuracy": [
                performance_test_nn,
                performance_test_rf,
                performance_test_knn,
                performance_test_dt,
                performance_test_nb,
            ],
        }
    )
    score_df = pd.concat([score_df, new_scores])


score_df = score_df.sort_values(by="accuracy", ascending=False)


plt.figure(figsize=(10, 10))
sns.barplot(x="model", y="accuracy", hue="feature_set", data=score_df)
plt.xlabel("Model")  # Fixed typo: "=" changed to " "
plt.ylabel("Accuracy")  # Fixed typo: "-" changed to "="
plt.ylim(0.7, 1)
plt.legend(loc="lower right")  # Fixed typo: "rigth" changed to "right"
plt.show()


#Select best model and evaluate results

(
    class_train_y,
    class_test_y,
    class_train_prob_y,
    class_test_prob_y,
    trained_model
) = learner.feedforward_neural_network(
    X_train[feature_set_3], y_train, X_test[feature_set_3], gridsearch=False
)
X_train[feature_set_3].columns
X_train[feature_set_3].index
X_train[feature_set_3].info()

accuracy = accuracy_score(y_test, class_test_y)

classes = class_test_prob_y.columns
cm = confusion_matrix(y_test, class_test_y, labels=classes)

# create confusion matrix for cm
plt.figure(figsize=(10, 10))
plt.imshow(cm, interpolation="nearest", cmap=plt.cm.Blues)
plt.title("Confusion matrix")
plt.colorbar()
tick_marks = np.arange(len(classes))
plt.xticks(tick_marks, classes, rotation=45)
plt.yticks(tick_marks, classes)

thresh = cm.max() / 2.0
for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
    plt.text(
        j,
        i,
        format(cm[i, j]),
        horizontalalignment="center",
        color="white" if cm[i, j] > thresh else "black",
    )
plt.ylabel("True label")
plt.xlabel("Predicted label")
plt.grid(False)
plt.show()

#save model to file
import pickle

model_filename = 'trained_model_final.pkl'
with open(model_filename, 'wb') as file:
    pickle.dump(trained_model, file)

print(f"Model has been saved to {model_filename}")


