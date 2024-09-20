package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class UserDAO {
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean loginUser(String email, String password) {
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USERS,
                new String[]{MyDatabaseHelper.COLUMN_ID},
                MyDatabaseHelper.COLUMN_EMAIL + "=? AND " + MyDatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        boolean loginSuccess = cursor.getCount() > 0;
        cursor.close();
        return loginSuccess;
    }

    public long addUser(String email, String password, String name, String birthdate, double weight, double height, String gender) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_EMAIL, email);
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, password);
        values.put(MyDatabaseHelper.COLUMN_NAME, name);
        values.put(MyDatabaseHelper.COLUMN_BIRTHDATE, birthdate);  // Store datetime as TEXT
        values.put(MyDatabaseHelper.COLUMN_WEIGHT, weight);
        values.put(MyDatabaseHelper.COLUMN_HEIGHT, height);
        values.put(MyDatabaseHelper.COLUMN_GENDER, gender);
        return database.insert(MyDatabaseHelper.TABLE_USERS, null, values);
    }

    public Cursor getUserDetails(String email) {
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USERS,
                null, // All columns
                MyDatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public boolean updateUser(String email, String name, String birthdate, double weight, double height, String gender) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_NAME, name);
        values.put(MyDatabaseHelper.COLUMN_BIRTHDATE, birthdate);
        values.put(MyDatabaseHelper.COLUMN_WEIGHT, weight);
        values.put(MyDatabaseHelper.COLUMN_HEIGHT, height);
        values.put(MyDatabaseHelper.COLUMN_GENDER, gender);

        int rowsAffected = database.update(MyDatabaseHelper.TABLE_USERS, values, MyDatabaseHelper.COLUMN_EMAIL + "=?", new String[]{email});
        return rowsAffected > 0;
    }

    public Cursor getExercises(String email) {
        String query = "SELECT e." + MyDatabaseHelper.COLUMN_EXERCISE_DURATION + ", " +
                "e." + MyDatabaseHelper.COLUMN_EXERCISE_CALORIES + ", " +
                "et." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_NAME +
                " FROM " + MyDatabaseHelper.TABLE_EXERCISES + " e " +
                " JOIN " + MyDatabaseHelper.TABLE_EXERCISE_TYPES + " et " +
                " ON e." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID_FK + " = et." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID +
                " WHERE e." + MyDatabaseHelper.COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{email});
        return cursor;
    }

    public Cursor getPredefinedExercises() {
        // Fetch predefined exercises from exercise_types table
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_EXERCISE_TYPES,
                null, // All columns
                null, // No selection criteria
                null, // No selection args
                null, null, null);
        return cursor;
    }

    public long insertExercise(String email, int exerciseTypeId, long duration, double calories, String startTime, String endTime) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID_FK, exerciseTypeId);
        values.put(MyDatabaseHelper.COLUMN_EXERCISE_DURATION, duration);
        values.put(MyDatabaseHelper.COLUMN_EXERCISE_CALORIES, calories);
        values.put(MyDatabaseHelper.COLUMN_EXERCISE_START_TIME, startTime);
        values.put(MyDatabaseHelper.COLUMN_EXERCISE_END_TIME, endTime);
        return database.insert(MyDatabaseHelper.TABLE_EXERCISES, null, values);
    }

    public Cursor getTotalWorkouts() {
        String query = "SELECT COUNT(*) AS totalWorkouts FROM " + MyDatabaseHelper.TABLE_EXERCISES;
        return database.rawQuery(query, null);
    }

    public Cursor getTotalDurationAndCalories() {
        String query = "SELECT SUM(" + MyDatabaseHelper.COLUMN_EXERCISE_DURATION + ") AS totalDuration, " +
                "SUM(" + MyDatabaseHelper.COLUMN_EXERCISE_CALORIES + ") AS totalCalories " +
                "FROM " + MyDatabaseHelper.TABLE_EXERCISES;
        return database.rawQuery(query, null);
    }

    public Cursor getTotalDurationAndCaloriesByTimeframe(long startTime, long endTime) {
        String query = "SELECT SUM(" + MyDatabaseHelper.COLUMN_EXERCISE_DURATION + ") AS totalDuration, " +
                "SUM(" + MyDatabaseHelper.COLUMN_EXERCISE_CALORIES + ") AS totalCalories " +
                "FROM " + MyDatabaseHelper.TABLE_EXERCISES + " " +
                "WHERE datetime(" + MyDatabaseHelper.COLUMN_EXERCISE_START_TIME + ") >= datetime(?) " +
                "AND datetime(" + MyDatabaseHelper.COLUMN_EXERCISE_END_TIME + ") <= datetime(?)";
        return database.rawQuery(query, new String[]{String.valueOf(startTime), String.valueOf(endTime)});
    }

    public Cursor getExerciseTypeIdByName(String exerciseName) {
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_EXERCISE_TYPES,
                new String[]{MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID},
                MyDatabaseHelper.COLUMN_EXERCISE_TYPE_NAME + "=?",
                new String[]{exerciseName},
                null, null, null);
        return cursor;
    }
/*
    public Cursor getTotalWorkoutsByDateRange(String email, long startTime, long endTime) {
        String query = "SELECT COUNT(*) AS totalWorkouts FROM " + MyDatabaseHelper.TABLE_EXERCISES +
                " WHERE " + MyDatabaseHelper.COLUMN_USER_EMAIL + " = ?" +
                " AND " + MyDatabaseHelper.COLUMN_EXERCISE_START_TIME + " >= ?" +
                " AND " + MyDatabaseHelper.COLUMN_EXERCISE_END_TIME + " <= ?";
        return database.rawQuery(query, new String[]{email, String.valueOf(startTime), String.valueOf(endTime)});
    }*/


    private String convertToISO8601(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public Cursor getTotalWorkoutsByDateRange(String email, long startTime, long endTime) {
        String startISO = convertToISO8601(startTime);
        String endISO = convertToISO8601(endTime);

        String query = "SELECT COUNT(*) AS totalWorkouts FROM " + MyDatabaseHelper.TABLE_EXERCISES +
                " WHERE " + MyDatabaseHelper.COLUMN_USER_EMAIL + "=? AND " +
                MyDatabaseHelper.COLUMN_EXERCISE_START_TIME + " BETWEEN ? AND ?";
        String[] selectionArgs = { email, startISO, endISO };
        return database.rawQuery(query, selectionArgs);
    }

    public Cursor getTotalDurationAndCaloriesByDateRange(String email, long startTime, long endTime) {
        String startISO = convertToISO8601(startTime);
        String endISO = convertToISO8601(endTime);

        String query = "SELECT SUM(" + MyDatabaseHelper.COLUMN_EXERCISE_DURATION + ") AS totalDuration, " +
                "SUM(" + MyDatabaseHelper.COLUMN_EXERCISE_CALORIES + ") AS totalCalories FROM " + MyDatabaseHelper.TABLE_EXERCISES +
                " WHERE " + MyDatabaseHelper.COLUMN_USER_EMAIL + "=? AND " +
                MyDatabaseHelper.COLUMN_EXERCISE_START_TIME + " BETWEEN ? AND ?";
        String[] selectionArgs = { email, startISO, endISO };
        return database.rawQuery(query, selectionArgs);
    }

    public Cursor getAggregatedExercisesByDateRange(String userEmail, long startTime, long endTime) {
        String startDate = convertToISO8601(startTime);
        String endDate = convertToISO8601(endTime);

        String query = "SELECT et." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_NAME + " AS exerciseType, " +
                "COALESCE(SUM(e." + MyDatabaseHelper.COLUMN_EXERCISE_DURATION + "), 0) AS totalDuration, " +
                "COALESCE(SUM(e." + MyDatabaseHelper.COLUMN_EXERCISE_CALORIES + "), 0) AS totalCalories " +
                "FROM " + MyDatabaseHelper.TABLE_EXERCISE_TYPES + " et " +
                "LEFT JOIN " + MyDatabaseHelper.TABLE_EXERCISES + " e ON et." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID + " = e." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_ID_FK + " " +
                "AND e." + MyDatabaseHelper.COLUMN_USER_EMAIL + " = ? " +
                "AND e." + MyDatabaseHelper.COLUMN_EXERCISE_START_TIME + " >= ? " +
                "AND e." + MyDatabaseHelper.COLUMN_EXERCISE_END_TIME + " <= ? " +
                "GROUP BY et." + MyDatabaseHelper.COLUMN_EXERCISE_TYPE_NAME;

        return database.rawQuery(query, new String[]{userEmail, startDate, endDate});
    }
}

