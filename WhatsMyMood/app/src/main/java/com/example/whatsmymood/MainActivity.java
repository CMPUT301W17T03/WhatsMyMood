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

public class MainActivity extends AppCompatActivity {
    private ListView moodList;

    private ArrayList<UserAccount> userList;
    private CurrentUser current = CurrentUser.getInstance();

    private ArrayList<String> followers;
    private ArrayList<UserAccount> mFollower;

    private ArrayList<Mood> moods;
    private ArrayAdapter<Mood> moodAdapter;

    //temporary
    private UserAccount user;
    private Follows templist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = current.getCurrentUser();
        user.getFollows().addToFollowing("TESTTEST");

        setContentView(R.layout.activity_main);
        LinearLayout footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);

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

    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
        ListView moodList = (ListView) findViewById(R.id.moodList);
        moodAdapter = new MoodAdapter(moods,this);

        moodList.setAdapter(moodAdapter);
    }

    protected void fetchData() {
        Log.d("tag", "fetch");

        ElasticSearchUserController.GetUserTask getFollowersTask = new ElasticSearchUserController.GetUserTask();
        for (String follower : followers) {
            getFollowersTask.execute(follower);

            try {
                mFollower = getFollowersTask.get();
                if (!mFollower.isEmpty()) {
                    UserAccount temp = mFollower.get(mFollower.size()-1);
                    MoodList tempMoodList = temp.getMoodList();
                    moods.add(new Mood(temp.getUsername(), tempMoodList.getRecentMood().getMoodType()));
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
