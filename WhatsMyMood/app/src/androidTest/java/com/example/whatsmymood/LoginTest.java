package com.example.whatsmymood;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ejtang on 2017-03-13.
 *
 * testing our Login activity to make sure that everything works
 * We may need to associate this with Elastic search and think of a way to
 * make it so that the accounts exist
 */
public class LoginTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testLoginWithExisting() {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void testLoginWithNonExisting() {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "Non-existing User");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        assertTrue(solo.waitForText("Wrong Username/Password Combination"));
    }

    public void testLoginWithoutPassword() {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "Non-existing User");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        assertTrue(solo.waitForText("This field is required"));
    }

    public void testLoginWithoutUsername() {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        assertTrue(solo.waitForText("This field is required"));
    }
}
