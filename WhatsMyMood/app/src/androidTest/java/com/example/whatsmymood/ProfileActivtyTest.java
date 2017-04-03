package com.example.whatsmymood;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ejtang on 2017-04-03.
 */

public class ProfileActivtyTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private CurrentUser current = CurrentUser.getInstance();

    public ProfileActivtyTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testOpenProfile() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.profile));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }

    public void testOpenAddMoodInProfile() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.profile));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));
    }

    public void testAddHappinessMoodInProfile() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.profile));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Happiness");
        solo.clickOnButton("Post");

        assertEquals("Happiness",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Happiness"));
    }
}
