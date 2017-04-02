package com.example.whatsmymood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
        ThemeController.setThemeForRecentMood(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_recent) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_moodType) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_moodMessage) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}