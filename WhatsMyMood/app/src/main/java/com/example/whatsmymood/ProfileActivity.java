package com.example.whatsmymood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * The type Profile activity.
 * This activity will allow the user to view the profile of other users
 * we will also display the moods posted by the user who's profile is
 * being viewed
 */
public class ProfileActivity extends AppCompatActivity {
    private final CurrentUser current = CurrentUser.getInstance();
    private UserAccount user = current.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeController.setThemeForRecentMood(this, current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LinearLayout footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);

        setAdapters();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //super.onActivityResult(requestCode, resultCode, intent);
        AddMoodController.processResult(requestCode, resultCode, intent);
    }

    private void setAdapters() {
        ArrayAdapter<Mood> moodAdapter = new MoodAdapter(user.getMoodList().getMoodList(), this);

        ListView moodListView = (ListView) findViewById(R.id.moodListView);

        moodListView.setAdapter(moodAdapter);
    }
}