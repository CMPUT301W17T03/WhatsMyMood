package com.example.whatsmymood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UserCreateActivity extends AppCompatActivity {

    private EditText userName ;
    private EditText password ;
    private EditText passwordConfirm ;
    private Button createAccount;
    private Button cancelButton;
    private CreateUserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        this.userName = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password1);
        this.passwordConfirm = (EditText) findViewById(R.id.password2);
        this.createAccount = (Button) findViewById(R.id.create);
        this.cancelButton = (Button) findViewById(R.id.cancel);

        this.userController = new CreateUserController();
    }

    public void clickCreate(){
        if ( password.getText().toString().equals(passwordConfirm.getText().toString())){
            userController.create(userName.getText().toString(),password.getText().toString());
        }
    }

    public void clickCancel(){

    }

}
