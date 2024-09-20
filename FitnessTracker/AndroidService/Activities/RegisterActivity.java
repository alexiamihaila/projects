package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.myapplication.R;
import com.example.myapplication.Database.UserDAO;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput, weightInput, heightInput, birthdateInput, ageInput;
    private AppCompatSpinner genderSpinner;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize input fields and spinner
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        weightInput = findViewById(R.id.Weight);
        heightInput = findViewById(R.id.height);
        birthdateInput = findViewById(R.id.Age);
        genderSpinner = findViewById(R.id.Gender);
        Button saveButton = findViewById(R.id.save_button);

        // Set up the gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Initialize the UserDAO
        userDAO = new UserDAO(this);
        userDAO.open();

        // Set the save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }

    private void saveUser() {
        // Get values from input fields
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String weightStr = weightInput.getText().toString().trim();
        String heightStr = heightInput.getText().toString().trim();
        String birthdate = birthdateInput.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        // Validate the inputs
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = weightStr.isEmpty() ? 0 : Double.parseDouble(weightStr);
        double height = heightStr.isEmpty() ? 0 : Double.parseDouble(heightStr);

        // Insert the user data into the database
        long result = userDAO.addUser(email, password, name, birthdate, weight, height, gender);
        if (result == -1) {
            Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            // Optionally, you can navigate to another activity or clear the input fields
            clearInputs();
        }
    }

    private void clearInputs() {
        nameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
        weightInput.setText("");
        heightInput.setText("");
        birthdateInput.setText("");
        genderSpinner.setSelection(0);
    }
}
