package com.example.whatsmymood;

import android.app.Activity;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ejtang on 2017-03-13.
 */

public class CreateAccountTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CreateAccountTest() {
        // we test with login activity because we have to go from the login screen
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testCreateUser() {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong Activity", UserCreateActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username), "NewUser");
        solo.enterText((EditText) solo.getView(R.id.password1), "password");
        solo.enterText((EditText) solo.getView(R.id.password2), "password");

        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }


}
