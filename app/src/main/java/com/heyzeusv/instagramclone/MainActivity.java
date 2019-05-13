package com.heyzeusv.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    Boolean signUpModeActive = true;
    TextView logInTextView;
    EditText usernameEditText;
    EditText passwordEditText;

    public void showUserList() {

        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

            signUpClicked(v);
        }
        return false;
    }

    public void switcher(View view) {

        Button signUpButton = findViewById(R.id.signUpButton);
        if (signUpModeActive) {

            signUpModeActive = false;
            signUpButton.setText(R.string.log_in);
            logInTextView.setText(R.string.or_sign_up);
        } else {

            signUpModeActive = true;
            signUpButton.setText(R.string.sign_up);
            logInTextView.setText(R.string.or_log_in);
        }
    }

    public void removeKeyboard(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void signUpClicked(View view) {

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

                            Log.i("Sign up", "Success!");
                            showUserList();
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
                            showUserList();
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

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        passwordEditText.setOnKeyListener(this);

        if (ParseUser.getCurrentUser() != null) {

            showUserList();
        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}
