package com.example.myapplication.Models;

public class Exercise {
    private String title;
    private String timeSpent;
    private int caloriesBurned;

    public Exercise(String title, String timeSpent, int caloriesBurned) {
        this.title = title;
        this.timeSpent = timeSpent;
        this.caloriesBurned = caloriesBurned;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }
}
