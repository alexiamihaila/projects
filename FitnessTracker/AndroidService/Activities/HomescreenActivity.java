package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Database.UserDAO;

public class HomescreenActivity extends AppCompatActivity {

    Button loginButton;
    EditText email_input, password_input;
    TextView register_label;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        email_input = findViewById(R.id.name_input);
        password_input = findViewById(R.id.password_homescreen);
        register_label = findViewById(R.id.register_label);

        userDAO = new UserDAO(this);
        userDAO.open();

        loginButton = findViewById(R.id.save_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Homescreen", "Login button clicked");

                final String email = String.valueOf(email_input.getText());
                final String password = String.valueOf(password_input.getText());

                if (!email.equals("") && !password.equals("")) {

                    new LoginTask().execute(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            return userDAO.loginUser(email, password);
        }

        @Override
        protected void onPostExecute(Boolean loginSuccess) {
            if (loginSuccess) {
                saveUserSession(email_input.getText().toString());
                if(email_input.getText().toString().equals("admin")){
                    Intent intent = new Intent(getApplicationContext(), CollectActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), TrackActivity.class);
                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveUserSession(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.apply();
    }
}
