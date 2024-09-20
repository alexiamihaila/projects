// StatsActivity.java
package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ExerciseAdapter;
import com.example.myapplication.Models.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Database.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private static final String TAG = "StatsActivity";
    private Spinner dateRangeSelector;
    private final String[] dateRanges = {"Last 7 Days", "Last 30 Days", "This Month", "Last Month", "This Year", "Custom Range"};
    private RecyclerView exerciseRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;
    private UserDAO userDAO;
    private String userEmail;
    private TextView totalWorkoutsTextView, totalDurationTextView, totalCaloriesTextView;
    private long startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        totalWorkoutsTextView = findViewById(R.id.total_workouts);
        totalDurationTextView = findViewById(R.id.total_duration);
        totalCaloriesTextView = findViewById(R.id.total_calories);

        dateRangeSelector = findViewById(R.id.dateRangeSelector);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dateRanges);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateRangeSelector.setAdapter(adapter);

        dateRangeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + dateRanges[position]);
                switch (position) {
                    case 0:
                        handleLast7Days();
                        break;
                    case 1:
                        handleLast30Days();
                        break;
                    case 2:
                        handleThisMonth();
                        break;
                    case 3:
                        handleLastMonth();
                        break;
                    case 4:
                        handleThisYear();
                        break;
                    case 5:
                        showDatePickerDialog();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        exerciseRecyclerView = findViewById(R.id.exerciseReclycerView);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseList = new ArrayList<>();
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        exerciseRecyclerView.setAdapter(exerciseAdapter);

        userDAO = new UserDAO(this);
        userDAO.open();

        userEmail = getUserSession();
        if (userEmail != null) {
            handleLast7Days(); // Load initial data for the last 7 days
        }
    }

    private String getUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

    private void handleLast7Days() {
        Log.d(TAG, "Handling Last 7 Days");
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        startTime = calendar.getTimeInMillis();
        loadStatistics();
        loadExerciseData();
    }

    private void handleLast30Days() {
        Log.d(TAG, "Handling Last 30 Days");
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        startTime = calendar.getTimeInMillis();
        loadStatistics();
        loadExerciseData();
    }

    private void handleThisMonth() {
        Log.d(TAG, "Handling This Month");
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        startTime = calendar.getTimeInMillis();
        loadStatistics();
        loadExerciseData();
    }

    private void handleLastMonth() {
        Log.d(TAG, "Handling Last Month");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        startTime = calendar.getTimeInMillis();
        loadStatistics();
        loadExerciseData();
    }

    private void handleThisYear() {
        Log.d(TAG, "Handling This Year");
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        startTime = calendar.getTimeInMillis();
        loadStatistics();
        loadExerciseData();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog startDatePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            Calendar startDate = Calendar.getInstance();
            startDate.set(year1, month1, dayOfMonth);
            startTime = startDate.getTimeInMillis();

            DatePickerDialog endDatePickerDialog = new DatePickerDialog(this, (view1, year2, month2, dayOfMonth1) -> {
                Calendar endDate = Calendar.getInstance();
                endDate.set(year2, month2, dayOfMonth1);
                endTime = endDate.getTimeInMillis();

                // Load statistics and exercises for custom date range
                Log.d(TAG, "Handling Custom Range: Start: " + startTime + ", End: " + endTime);
                loadStatistics();
                loadExerciseData();
            }, year, month, day);

            endDatePickerDialog.show();
        }, year, month, day);

        startDatePickerDialog.show();
    }

    private void loadExerciseData() {
        Log.d(TAG, "Loading exercise data from " + startTime + " to " + endTime);
        exerciseList.clear();
        Cursor cursor = userDAO.getAggregatedExercisesByDateRange(userEmail, startTime, endTime);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("exerciseType"));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow("totalDuration"));
                int calories = cursor.getInt(cursor.getColumnIndexOrThrow("totalCalories"));

                // Convert duration to hours, minutes, and seconds
                int hours = (int) (duration / 3600);
                int minutes = (int) ((duration % 3600) / 60);
                int seconds = (int) (duration % 60);
                String formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                Log.d(TAG, "Loaded exercise: " + title + ", Duration: " + formattedDuration + ", Calories: " + calories);
                exerciseList.add(new Exercise(title, formattedDuration, calories));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d(TAG, "No exercises found for the selected date range");
        }
        exerciseAdapter.notifyDataSetChanged();
    }

    private void loadStatistics() {
        loadTotalWorkouts();
        loadTotalDurationAndCalories();
    }

    private void loadTotalWorkouts() {
        Log.d(TAG, "Loading total workouts from " + startTime + " to " + endTime);
        Cursor cursor = userDAO.getTotalWorkoutsByDateRange(userEmail, startTime, endTime);
        if (cursor != null && cursor.moveToFirst()) {
            int totalWorkouts = cursor.getInt(cursor.getColumnIndexOrThrow("totalWorkouts"));
            Log.d(TAG, "Total workouts: " + totalWorkouts);
            totalWorkoutsTextView.setText(String.valueOf(totalWorkouts));
            cursor.close();
        } else {
            Log.d(TAG, "No workouts found for the selected date range");
            totalWorkoutsTextView.setText("0");
        }
    }

    private void loadTotalDurationAndCalories() {
        Log.d(TAG, "Loading total duration and calories from " + startTime + " to " + endTime);
        Cursor cursor = userDAO.getTotalDurationAndCaloriesByDateRange(userEmail, startTime, endTime);
        if (cursor != null && cursor.moveToFirst()) {
            long totalDurationInSeconds = cursor.getLong(cursor.getColumnIndexOrThrow("totalDuration"));
            int totalCalories = cursor.getInt(cursor.getColumnIndexOrThrow("totalCalories"));

            // Convert total duration in seconds to hours, minutes, and seconds
            int hours = (int) (totalDurationInSeconds / 3600);
            int minutes = (int) ((totalDurationInSeconds % 3600) / 60);
            int seconds = (int) (totalDurationInSeconds % 60);

            Log.d(TAG, "Total duration in seconds: " + totalDurationInSeconds);
            Log.d(TAG, "Converted to hours: " + hours + ", minutes: " + minutes + ", seconds: " + seconds);
            Log.d(TAG, "Total calories: " + totalCalories);

            totalDurationTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            totalCaloriesTextView.setText(String.valueOf(totalCalories));
            cursor.close();
        } else {
            Log.d(TAG, "No duration and calories found for the selected date range");
            totalDurationTextView.setText("00:00:00");
            totalCaloriesTextView.setText("0");
        }
    }

    private final BroadcastReceiver updateStatsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Broadcast received to update stats");
            loadStatistics();
            loadExerciseData();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(updateStatsReceiver, new IntentFilter("com.example.myapplication.UPDATE_STATS"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateStatsReceiver);
    }
}
