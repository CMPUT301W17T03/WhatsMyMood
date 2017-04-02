package com.example.whatsmymood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Main Activity
 * Used for displaying the moods of the people you follow
 * Also implements a footer that handles different activities
 */
public class MainActivity extends AppCompatActivity {
    private final CurrentUser current = CurrentUser.getInstance();

    private LinearLayout footer;
    private FooterHandler handler;

    private ArrayList<String> followers;

    private ArrayList<Mood> moods;

    private ArrayAdapter<Mood> moodAdapter;
    private ListView moodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("meep", "Main - onCreate");
        ThemeController.setThemeForRecentMood(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);

        fetchData();
        setAdapters();
    }

    /**
     * Sets the first query up every time main activity
     * is viewed. This ensures that we get the most updated
     * user.
     */
    @Override
    protected void onStart() {
        Log.i("meep", "Main - onStart");
        //ThemeController.setThemeForRecentMood(this, current.getCurrentUser().getMoodList().getRecentMood().getMoodType());
        super.onStart();

        fetchData();
        ((ArrayAdapter) moodListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        AddMoodController.processResult(requestCode, resultCode, intent);
    }



    private void setAdapters() {
        this.moodAdapter = new MoodAdapter(moods,this);

        // Sets the adapter
        this.moodListView = (ListView) findViewById(R.id.moodListView);
        this.moodListView.setAdapter(this.moodAdapter);
    }

    /**
     * Fetches the moods from each follower and adds them
     * to an ArrayList to be displayed
     */
    private void fetchData() {

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {
            UserAccount user = getUserTask.get().get(0);

            followers = user.getRelations().getFollowingList();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        moods = new ArrayList<>();

        for (String follower : followers) {
            ElasticSearchUserController.GetUserTask getFollowersTask = new ElasticSearchUserController.GetUserTask();
            getFollowersTask.execute(follower);

            try {
                ArrayList<UserAccount> mFollower = getFollowersTask.get();
                if (!mFollower.isEmpty()) {
                    UserAccount temp = mFollower.get(mFollower.size()-1);

                    // Exception
                    // If you follow one person and they have no moods
                    if (!(temp.getMoodList().getSize() == 0)) {
                        moods.add(temp.getMoodList().getRecentMood());
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}