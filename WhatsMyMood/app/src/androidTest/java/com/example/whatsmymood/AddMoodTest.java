package com.example.whatsmymood;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ejtang on 2017-04-03.
 */

public class AddMoodTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private CurrentUser current = CurrentUser.getInstance();

    public AddMoodTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
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

    public void testAddSadnessMoodInProfile() throws Exception {
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
        solo.clickOnText("Sadness");
        solo.clickOnButton("Post");

        assertEquals("Sadness",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Sadness"));
    }

    public void testAddConfusionMoodInProfile() throws Exception {
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
        solo.clickOnText("Confusion");
        solo.clickOnButton("Post");

        assertEquals("Confusion",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Confusion"));
    }

    public void testAddDisgustedMoodInProfile() throws Exception {
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
        solo.clickOnText("Disgusted");
        solo.clickOnButton("Post");

        assertEquals("Disgusted",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Disgusted"));
    }

    public void testAddScaredMoodInProfile() throws Exception {
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
        solo.clickOnText("Scared");
        solo.clickOnButton("Post");

        assertEquals("Scared",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Scared"));
    }

    public void testAddShameMoodInProfile() throws Exception {
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
        solo.clickOnText("Shame");
        solo.clickOnButton("Post");

        assertEquals("Shame",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Shame"));
    }

    public void testAddSurprisedMoodInProfile() throws Exception {
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
        solo.clickOnText("Surprised");
        solo.clickOnButton("Post");

        assertEquals("Surprised",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        assertTrue("most recent mood not on screen!", solo.searchText("Surprised"));
    }


}

