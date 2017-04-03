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

    public void testAddHappinessMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Happiness");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Happiness",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }

    public void testAddSadnessMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Sadness");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Sadness",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }

    public void testAddConfusionMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Confusion");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Confusion",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }

    public void testAddDisgustedMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Disgusted");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Disgusted",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }

    public void testAddScaredMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Scared");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Scared",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }

    public void testAddShameMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        solo.clickOnText("Shame");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Shame",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }

    public void testAddSurprisedMood() throws Exception {
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.Username), "testUser");
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add));
        assertTrue("Could not find the dialog!", solo.searchText("Select a mood"));

        solo.clickOnView(solo.getView(R.id.select_mood));
        
        solo.clickOnText("Surprised");
        solo.clickOnButton("Post");

        solo.sleep(500);

        assertEquals("Surprised",current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
    }


}

