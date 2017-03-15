package com.example.whatsmymood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Creates a new user
 * Used before logging in if the user is new
 */
public class UserCreateActivity extends AppCompatActivity {

    private CreateUserController userController;

    /**
     * Instantiates each EditText and grabs the user input values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        final EditText userName = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password1);
        final EditText passwordConfirm = (EditText) findViewById(R.id.password2);
        final Button createAccount = (Button) findViewById(R.id.create);
        final Button cancelButton = (Button) findViewById(R.id.cancel);

        this.userController = new CreateUserController();

        // Error checking if the username already exists
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    if (userController.create(userName.getText().toString(), password.getText().toString())) {
                        finish();
                    } else {
                        String errorString = "Username already exists";
                        Toast errorMessage = Toast.makeText(UserCreateActivity.this, errorString, Toast.LENGTH_SHORT);
                        errorMessage.show();
                    }

                } else {
                    String errorString = "Passwords do not match";
                    Toast errorMessage = Toast.makeText(UserCreateActivity.this, errorString, Toast.LENGTH_SHORT);
                    errorMessage.show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
