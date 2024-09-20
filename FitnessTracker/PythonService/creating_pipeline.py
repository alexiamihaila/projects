import pandas as pd
import numpy as np
from sklearn.pipeline import Pipeline, FeatureUnion
from sklearn.base import BaseEstimator, TransformerMixin
from sklearn.cluster import KMeans
import joblib
from butterworth_filter import *

# Define custom transformers
def display_intermediate_data(data, step_name):
    print(f"Data after {step_name}:")
    print(data.head())  # Display the first few rows of the DataFrame
    print("\n")

class RemoveNullsAndSpaces(BaseEstimator, TransformerMixin):
    def fit(self, X, y=None):
        return self
    
    def transform(self, X):
        X = X.dropna()
        X.columns = X.columns.str.replace(' ', '')
        X = X.apply(lambda x: x.str.strip() if x.dtype == "object" else x)
        display_intermediate_data(X, "RemoveNullsAndSpaces")
        return X

class ConvertToDatetime(BaseEstimator, TransformerMixin):
    def fit(self, X, y=None):
        return self
    
    def transform(self, X):
        if 'Timestamp' in X.columns:
            X['Timestamp'] = pd.to_datetime(X['Timestamp'])
            X.index = pd.to_datetime(X['Timestamp'])
        display_intermediate_data(X, "ConvertToDatetime")
        return X

class LowpassFilter(BaseEstimator, TransformerMixin):
    def __init__(self, cutoff_freq, sf, order, columns):
        self.cutoff_freq = cutoff_freq
        self.sf = sf
        self.order = order
        self.columns = columns
    
    def fit(self, X, y=None):
        return self
    
    def transform(self, X):
        for col in self.columns:
            X = lowpass_filter(X, self.cutoff_freq, self.sf, self.order, col)
            X[col] = X[col + "_lowpass"]
            del X[col + "_lowpass"]
        display_intermediate_data(X, "LowpassFilter")
        return X

class SumOfSquares(BaseEstimator, TransformerMixin):
    def fit(self, X, y=None):
        return self
    
    def transform(self, X):
        X["acc_r"] = np.sqrt(np.power(X['X_accelerom'],2) +  np.power(X['Y_accelerom'],2) + np.power(X['Z_accelerom'],2)).to_numpy()
        display_intermediate_data(X, "SumOfSquares")
        return X

class RollingStats(BaseEstimator, TransformerMixin):
    def __init__(self, columns):
        self.columns = columns
    
    def fit(self, X, y=None):
        return self
    
    def transform(self, X):
        temporal_list = []
        for et in X["Exercise_Type"].unique():
            subset = X[X["Exercise_Type"] == et].copy()
            for col in self.columns:
                subset[col + "_temp_mean"] = (subset[col].rolling(6).apply(np.mean))
                subset[col + "_temp_std"] = (subset[col].rolling(6).apply(np.std))
            temporal_list.append(subset)
        X = pd.concat(temporal_list)
        X = X.dropna()
        display_intermediate_data(X, "RollingStats")
        return X



class KMeansClustering(BaseEstimator, TransformerMixin):
    def __init__(self, n_clusters, columns, kmeans):
        self.n_clusters = n_clusters
        self.columns = columns
    
    def fit(self, X, y=None):
        self.kmeans = KMeans(n_clusters=self.n_clusters, n_init=15, random_state=0)
        self.kmeans.fit(X[self.columns])
        return self

    
    def transform(self, X):
        X["cluster"] = self.kmeans.predict(X[self.columns])
        display_intermediate_data(X, "KMeansClustering")
        return X
    
pipeline = Pipeline([
    ('remove_nulls_and_spaces', RemoveNullsAndSpaces()),
    ('convert_to_datetime', ConvertToDatetime()),
    ('lowpass_filter', LowpassFilter(cutoff_freq=1.3, sf=1000/200, order=5, columns=["X_accelerom", "Y_accelerom", "Z_accelerom"])),
    ('sum_of_squares', SumOfSquares()),
    ('rolling_stats', RollingStats(columns=["X_accelerom", "Y_accelerom", "Z_accelerom", "acc_r"])),
    ('kmeans_clustering', KMeansClustering(n_clusters=5, columns=["X_accelerom", "Y_accelerom", "Z_accelerom"], kmeans=None))
])

df = pd.read_csv(r"C:\Users\alexi\IdeaProjects\LicentaConsumer\Measurements_1714127497745.csv")

pipeline.fit(df)

joblib.dump(pipeline, 'fitness_pipeline.pkl')