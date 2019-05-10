package com.heyzeusv.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Boolean signUpModeActive = true;
    TextView logInTextView;

    public void switcher(View view) {

        Button signUpButton = findViewById(R.id.signUpButton);
        if (signUpModeActive) {

            signUpModeActive = false;
            signUpButton.setText("Login");
            logInTextView.setText("Or, Sign Up");
        } else {

            signUpModeActive = true;
            signUpButton.setText("Sign Up");
            logInTextView.setText("Or, Log In");
        }
    }

    public void signUpClicked(View view) {

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        if (usernameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {

            Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
        } else {

            if (signUpModeActive) {

                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Log.i("Signup", "Success!");
                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null) {

                            Log.i("Log In", "Okay!");
                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInTextView = findViewById(R.id.logInTextView);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
