package com.example.whatsmymood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserCreateActivity extends AppCompatActivity {

    private CreateUserController userController;

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
