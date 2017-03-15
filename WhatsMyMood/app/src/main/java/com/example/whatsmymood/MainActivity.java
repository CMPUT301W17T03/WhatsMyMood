package com.example.whatsmymood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private LinearLayout footer;
    private FooterHandler handler;

    private final CurrentUser current = CurrentUser.getInstance();

    private ArrayList<String> followers;

    private ArrayList<Mood> moods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        footer = (LinearLayout)findViewById(R.id.footer);
    }

    /**
     * Sets the first query up every time main activity
     * is viewed. This ensures that we get the most updated
     * user.
     */
    @Override
    protected void onStart() {
        super.onStart();

        FooterHandler handler = new FooterHandler(this, footer);

        fetchData();
        setAdapters();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        AddMoodController.processResult(requestCode, resultCode, intent);
    }

    private void setAdapters() {
        ArrayAdapter<Mood> moodAdapter = new MoodAdapter(moods,this);

        // Sets the adapter
        ListView moodList = (ListView) findViewById(R.id.moodList);

        moodList.setAdapter(moodAdapter);
    }

    /**
     * Fetches the moods from each follower and adds them
     * to an ArrayList to be displayed
     */
    private void fetchData() {

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {
            ArrayList<UserAccount> userList = getUserTask.get();

            followers = userList.get(0).getFollows().getFollowingList();

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
                    moods.add(temp.getMoodList().getRecentMood());
                } else {
                    //TODO: Handle exception
                    //Tbh even if we don't handle it nothing goes wrong
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
