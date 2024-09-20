package com.example.myapplication.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.myapplication.Database.MyDatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.Database.UserDAO;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameInput, weightInput, heightInput, birthdateInput;
    private AppCompatSpinner genderSpinner;
    private UserDAO userDAO;
    private String userEmail;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize input fields and spinner
        nameInput = findViewById(R.id.name_input);
        weightInput = findViewById(R.id.Weight);
        heightInput = findViewById(R.id.height);
        birthdateInput = findViewById(R.id.Age);
        genderSpinner = findViewById(R.id.Gender);
        saveButton = findViewById(R.id.save_button);

        // Set up the gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Initialize the UserDAO
        userDAO = new UserDAO(this);
        userDAO.open();

        // Fetch and display user details
        userEmail = getUserSession();
        if (userEmail != null) {
            displayUserDetails(userEmail);
        }

        // Set the save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }

    private String getUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

    private void displayUserDetails(String email) {
        Cursor cursor = userDAO.getUserDetails(email);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_NAME));
            String weight = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_WEIGHT));
            String height = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_HEIGHT));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_BIRTHDATE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_GENDER));

            // Set the retrieved data to the input fields
            nameInput.setText(name);
            weightInput.setText(weight);
            heightInput.setText(height);
            birthdateInput.setText(birthdate);
            setSpinnerSelection(genderSpinner, gender);
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void setSpinnerSelection(AppCompatSpinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        if (adapter != null) {
            for (int position = 0; position < adapter.getCount(); position++) {
                if (adapter.getItem(position).toString().equalsIgnoreCase(value)) {
                    spinner.setSelection(position);
                    return;
                }
            }
        }
    }

    private void updateUserProfile() {
        // Get values from input fields
        String name = nameInput.getText().toString().trim();
        String weightStr = weightInput.getText().toString().trim();
        String heightStr = heightInput.getText().toString().trim();
        String birthdate = birthdateInput.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        if (name.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() || birthdate.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);

        // Update the user data in the database
        boolean isUpdated = userDAO.updateUser(userEmail, name, birthdate, weight, height, gender);
        if (isUpdated) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}
