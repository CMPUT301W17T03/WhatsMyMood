package com.example.whatsmymood;

import android.icu.util.ValueIterator;
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
    private ListView moodList;

    private ArrayList<UserAccount> userList;
    private CurrentUser current = CurrentUser.getInstance();

    private ArrayList<String> followers;
    private ArrayList<UserAccount> mFollower;

    // Checks for follower updates
    private static int size = 0;

    private ArrayList<Mood> moods;
    private ArrayAdapter<Mood> moodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);
    }

    /**
     * Sets the first query up everytime main activity
     * is viewed. This ensures that we get the most updated
     * user.
     */
    @Override
    protected void onStart() {
        super.onStart();

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {
            userList = getUserTask.get();

            // Should be one user
            // If there is more than one user then the world is dying
            for (UserAccount User : userList) {
                followers = User.getFollows().getFollowingList();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Instantiate out adapter and set```` view for viewing moods
        moods = new ArrayList<Mood>();

        if (followers.size() != size) {
            fetchData();
            size = followers.size();
        }

        moodList = (ListView) findViewById(R.id.moodList);
        moodAdapter = new MoodAdapter(moods,this);

        moodList.setAdapter(moodAdapter);
    }

    /**
     * Fetches the moods from each follower and adds them
     * to a arraylist to be displayed
     */
    protected void fetchData() {
        for (String follower : followers) {
            ElasticSearchUserController.GetUserTask getFollowersTask = new ElasticSearchUserController.GetUserTask();
            getFollowersTask.execute(follower);

            try {
                mFollower = getFollowersTask.get();
                if (!mFollower.isEmpty()) {
                    UserAccount temp = mFollower.get(mFollower.size()-1);
                    MoodList tempMoodList = temp.getMoodList();
                    moods.add(temp.getMoodList().getRecentMood());
                } else {
                    //TODO: Handle exception
                    //Tbh even if we don't handle it nothing goes wrong
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
