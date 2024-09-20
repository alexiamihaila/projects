package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "FitnessTracker.db";
    private static final int DATABASE_VERSION = 6;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id_user";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_GENDER = "gender";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_BIRTHDATE + " TEXT, " +  // Use TEXT to store datetime
                    COLUMN_WEIGHT + " REAL, " +
                    COLUMN_HEIGHT + " REAL, " +
                    COLUMN_GENDER + " TEXT);";

    // Exercise types table
    public static final String TABLE_EXERCISE_TYPES = "exercise_types";
    public static final String COLUMN_EXERCISE_TYPE_ID = "id_exercise_type";
    public static final String COLUMN_EXERCISE_TYPE_NAME = "name";

    // Exercises table
    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EXERCISE_ID = "id_exercise";
    public static final String COLUMN_EXERCISE_DURATION = "duration";
    public static final String COLUMN_EXERCISE_CALORIES = "calories";
    public static final String COLUMN_USER_EMAIL = "user_email"; // To link exercises to users
    public static final String COLUMN_EXERCISE_TYPE_ID_FK = "exercise_type_id";// Foreign key to exercise types
    public static final String COLUMN_EXERCISE_START_TIME = "start_time";
    public static final String COLUMN_EXERCISE_END_TIME = "end_time";

    private static final String CREATE_TABLE_EXERCISE_TYPES = "CREATE TABLE " + TABLE_EXERCISE_TYPES + " (" +
            COLUMN_EXERCISE_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EXERCISE_TYPE_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_EXERCISES = "CREATE TABLE " + TABLE_EXERCISES + " (" +
            COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EXERCISE_DURATION + " INTEGER NOT NULL, " + // Store duration in seconds
            COLUMN_EXERCISE_CALORIES + " INTEGER NOT NULL, " +
            COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
            COLUMN_EXERCISE_TYPE_ID_FK + " INTEGER, " +
            COLUMN_EXERCISE_START_TIME + " TEXT NOT NULL, " +
            COLUMN_EXERCISE_END_TIME + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_EMAIL + "), " +
            "FOREIGN KEY (" + COLUMN_EXERCISE_TYPE_ID_FK + ") REFERENCES " + TABLE_EXERCISE_TYPES + "(" + COLUMN_EXERCISE_TYPE_ID + "));";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EXERCISE_TYPES);
        db.execSQL(CREATE_TABLE_EXERCISES);

        // Insert predefined exercise types
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Bicep Curls');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Shoulder Press');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Bent-over Rows');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Lateral Raises');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Squats');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Plank');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Lunges');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('Romanian Deadlift');");
        db.execSQL("INSERT INTO " + TABLE_EXERCISE_TYPES + " (" + COLUMN_EXERCISE_TYPE_NAME + ") VALUES ('No Activity');");

        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ") VALUES ('admin', 'admin');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

}
