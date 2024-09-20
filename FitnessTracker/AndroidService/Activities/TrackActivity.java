package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.AccelerometerData;
import com.example.myapplication.Database.MyDatabaseHelper;
import com.example.myapplication.Database.UserDAO;
import com.example.myapplication.Models.ExerciseRecord;
import com.example.myapplication.R;
import com.example.myapplication.RabbitMQ.RabbitMQHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TrackActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button startButton, stopButton;
    private boolean isCollectingData = false;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private TextView workoutTimeTextView, currentExerciseTextView;
    private long startTime;

    private ArrayList<AccelerometerData> accelerometerDataList = new ArrayList<>();
    private RabbitMQHelper rabbitMQHelper;

    private List<AccelerometerData> batchDataList = new ArrayList<>();

    private static final int WINDOW_SIZE = 12 * 5; // 12 seconds at 5 samples per second
    private static final int STEP_SIZE = 2 * 5; // Step size of 2 seconds at 5 samples per second
    private List<AccelerometerData> slidingWindowData = new ArrayList<>();

    private static final String SENSOR_DATA_QUEUE = "sensor_data_queue";
    private static final String PREDICTED_EXERCISE_QUEUE = "predicted_exercise_queue";

    //userprofile
    private double userWeight;
    private double userHeight;
    private int userAge;
    private String userGender;

    //saving the exercise
    private long workoutStartTime;
    private long lastExerciseStartTime;
    private String lastPredictedExercise;
    private List<ExerciseRecord> exerciseRecords = new ArrayList<>();
    private String TAG = "TrackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        workoutTimeTextView = findViewById(R.id.workout_time);
        currentExerciseTextView = findViewById(R.id.current_exercise);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);

        // Add listeners to start or stop exercising
        startButton.setOnClickListener(v -> {
            Log.d("TrackActivity", "Start Button Clicked");
            startDataCollection();
        });

        stopButton.setOnClickListener(v -> {
            Log.d("TrackActivity", "Stop Button Clicked");
            stopDataCollection();
        });

        // Initialize timer runnable
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;
                workoutTimeTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                timerHandler.postDelayed(this, 500);
            }
        };

        // Click profiles
        ImageView profileIcon = findViewById(R.id.profile_icon);
        ImageView monitoringIcon = findViewById(R.id.monitoring_icon);

        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        monitoringIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
            startActivity(intent);
        });

        fetchUserProfileData();

        // Initialize RabbitMQ in a background thread
        new Thread(() -> {
            try {
                rabbitMQHelper = new RabbitMQHelper("amqp://gwqwxywo:ntgFhoV5MGfdg-drZjLtPBHYRnSJvx1C@goose.rmq2.cloudamqp.com/gwqwxywo");
                rabbitMQHelper.declareQueue(SENSOR_DATA_QUEUE);
                rabbitMQHelper.declareQueue(PREDICTED_EXERCISE_QUEUE);
                startConsumingPredictions(); // Start consuming after RabbitMQ initialization
            } catch (Exception e) {
                Log.e("RabbitMQ", "Initialization failed", e);
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isCollectingData) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(new Date());

            AccelerometerData data = new AccelerometerData(x, y, z, timestamp);
            slidingWindowData.add(data);

            if (slidingWindowData.size() >= WINDOW_SIZE) {
                sendBatchToRabbitMQ();
                for (int i = 0; i < STEP_SIZE; i++) {
                    slidingWindowData.remove(0);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    private void startDataCollection() {
        isCollectingData = true;
        Log.d("TrackActivity", "Data collection started");

        workoutStartTime = System.currentTimeMillis();
        startTime = workoutStartTime;
        lastExerciseStartTime = workoutStartTime; // Initialize the start time of the first exercise
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void stopDataCollection() {
        isCollectingData = false;
        Log.d("TrackActivity", "Data collection stopped");

        long workoutEndTime = System.currentTimeMillis();
        timerHandler.removeCallbacks(timerRunnable);

        // Log the collected data
        for (AccelerometerData data : slidingWindowData) {
            Log.d("TrackActivity1", data.toString());
        }

        // Record the last exercise if it hasn't been recorded yet
        if (lastPredictedExercise != null) {
            Log.d("TrackActivity", "Recording last exercise: " + lastPredictedExercise + " from " + lastExerciseStartTime + " to " + workoutEndTime);
            recordExercise(lastPredictedExercise, lastExerciseStartTime, workoutEndTime);
        }

        // Record the workout and exercises
        recordWorkout(workoutStartTime, workoutEndTime);

        // Clear the list for future use
        slidingWindowData.clear();
    }

    private void sendBatchToRabbitMQ() {
        new Thread(() -> {
            try {
                String message = AccelerometerData.toJson(slidingWindowData);
                rabbitMQHelper.publishMessage(SENSOR_DATA_QUEUE, message);
            } catch (Exception e) {
                Log.e("RabbitMQ", "Publishing failed", e);
            }
        }).start();
    }

    private void startConsumingPredictions() {
        new Thread(() -> {
            try {
                Channel channel = rabbitMQHelper.getChannel();
                channel.queueDeclare(PREDICTED_EXERCISE_QUEUE, false, false, false, null);

                DefaultConsumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, StandardCharsets.UTF_8);
                        runOnUiThread(() -> updatePredictedExercise(message));
                        Log.d(TAG, "handleDelivery: s-a ajuns aici: " + message);
                    }
                };
                channel.basicConsume(PREDICTED_EXERCISE_QUEUE, true, consumer);
            } catch (Exception e) {
                Log.e("RabbitMQ", "Failed to consume predictions", e);
            }
        }).start();
    }

    private void updatePredictedExercise(String message) {
        try {
            JSONObject json = new JSONObject(message);
            String predictedExercise = json.getString("predicted_exercise");
            Log.d(TAG, "Sa ajuns in updatepredicted iar exercitiul e: " + predictedExercise);

            // If a new exercise is detected, record the previous one
            if (lastPredictedExercise != null && !lastPredictedExercise.equals(predictedExercise)) {
                long currentTime = System.currentTimeMillis();
                Log.d("TrackActivity", "Recording exercise: " + lastPredictedExercise + " from " + lastExerciseStartTime + " to " + currentTime);
                recordExercise(lastPredictedExercise, lastExerciseStartTime, currentTime);
                lastExerciseStartTime = currentTime; // Reset start time for the new exercise
            }

            lastPredictedExercise = predictedExercise; // Update last predicted exercise
            currentExerciseTextView.setText(predictedExercise);
        } catch (Exception e) {
            Log.e("UpdateExercise", "Failed to update predicted exercise", e);
        }
    }

    private void recordWorkout(long startTime, long endTime) {
        for (ExerciseRecord record : exerciseRecords) {
            insertExerciseRecord(record);
        }
        exerciseRecords.clear(); // Clear the records after storing
    }

    private void recordExercise(String exercise, long startTime, long endTime) {
        long duration = (endTime - startTime) / 1000; // Duration in seconds
        double calories = calculateCalories(exercise, duration);
        ExerciseRecord record = new ExerciseRecord(getUserSession(), exercise, duration, calories, startTime, endTime);
        exerciseRecords.add(record);
        Log.d("TrackActivity", "Exercise recorded: " + exercise + ", Duration: " + duration + " seconds, Calories: " + calories + ", Start Time: " + record.getStartTime() + ", End Time: " + record.getEndTime());
    }

    private void insertExerciseRecord(ExerciseRecord record) {
        UserDAO userDAO = new UserDAO(this);
        userDAO.open();

        Cursor cursor = userDAO.getExerciseTypeIdByName(record.getExerciseType());
        if (cursor != null && cursor.moveToFirst()) {
            int exerciseTypeId = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID));
            cursor.close();

            long newRowId = userDAO.insertExercise(record.getEmail(), exerciseTypeId, record.getDuration(), record.getCalories(), record.getStartTime(), record.getEndTime());
            if (newRowId == -1) {
                Log.e("TrackActivity", "Failed to insert exercise record");
            } else {
                Log.d("TrackActivity", "Exercise record inserted successfully");
                // Optionally broadcast an intent to update stats
                Intent intent = new Intent("com.example.myapplication.UPDATE_STATS");
                sendBroadcast(intent);
            }
        } else {
            Log.e("TrackActivity", "Failed to find exercise type ID");
        }

        userDAO.close();
    }

    private double calculateCalories(String exercise, long duration) {
        double metValue;

        switch (exercise.toLowerCase()) {
            case "bicep curls":
                metValue = 3.0;
                break;
            case "shoulder press":
                metValue = 4.0;
                break;
            case "bent-over rows":
                metValue = 4.0;
                break;
            case "lateral raises":
                metValue = 3.5;
                break;
            default:
                metValue = 3.0; // Default MET value
                break;
        }

        // Calculate BMR using Harris-Benedict equation
        double bmr;
        if (userGender.equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * userWeight) + (4.799 * userHeight) - (5.677 * userAge);
        } else {
            bmr = 447.593 + (9.247 * userWeight) + (3.098 * userHeight) - (4.330 * userAge);
        }

        // Calories burned calculation
        double durationInHours = duration / 3600.0; // Convert duration to hours
        return metValue * bmr * (durationInHours / 24.0); // Normalized to a day
    }


    private String getUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
    private void fetchUserProfileData() {
        UserDAO userDAO = new UserDAO(this);
        userDAO.open();
        String email = getUserSession();
        Cursor cursor = userDAO.getUserDetails(email);
        if (cursor != null && cursor.moveToFirst()) {
            userWeight = cursor.getDouble(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_WEIGHT));
            userHeight = cursor.getDouble(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_HEIGHT));
            userGender = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_GENDER));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_BIRTHDATE));
            if (isValidDate(birthdate)) {
                userAge = calculateAge(birthdate);
            } else {
                userAge = 30; // Default age if parsing fails
                Log.e("TrackActivity", "Invalid birthdate format: " + birthdate);
            }
            cursor.close();
        } else {
            userWeight = 70.0; // Default weight if not found
            userHeight = 170.0; // Default height if not found
            userGender = "male"; // Default gender if not found
            userAge = 30; // Default age if not found
        }
        userDAO.close();
    }

    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int calculateAge(String birthdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(birthdate);
            Calendar dob = Calendar.getInstance();
            dob.setTime(date);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        } catch (Exception e) {
            e.printStackTrace();
            return 30; // Default age if parsing fails
        }
    }
}