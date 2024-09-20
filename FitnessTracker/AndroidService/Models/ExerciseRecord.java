package com.example.myapplication.Models;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class ExerciseRecord {
    private String email;
    private String exerciseType;
    private long duration;
    private double calories;
    private String startTime;
    private String endTime;

    public ExerciseRecord(String email, String exerciseType, long duration, double calories, long startTime, long endTime) {
        this.email = email;
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.calories = calories;
        this.startTime = formatTime(startTime);
        this.endTime = formatTime(endTime);
    }

    public String getEmail() {
        return email;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public long getDuration() {
        return duration;
    }

    public double getCalories() {
        return calories;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    private String formatTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(new Date(time));

    }
}