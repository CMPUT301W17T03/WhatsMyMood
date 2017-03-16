package com.example.whatsmymood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class ProfileActivty extends AppCompatActivity {
    private final CurrentUser current = CurrentUser.getInstance();

    private LinearLayout footer;
    private FooterHandler handler;

    private ArrayList<Mood>  moods;
    private MoodList moodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        footer = (LinearLayout)findViewById(R.id.footer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FooterHandler handler = new FooterHandler(this, footer);

        fetchData();
        setAdapters();
    }

    private void setAdapters() {
        ArrayAdapter<Mood> moodAdapter = new MoodAdapter(moods,this);

        // Sets the adapter
        ListView moodListView = (ListView) findViewById(R.id.moodListView);

        moodListView.setAdapter(moodAdapter);
    }

    /**
     * Fetches all moods of the current user and adds them
     * to an ArrayList to be displayed
     */
    private void fetchData() {

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {
            UserAccount user = getUserTask.get().get(0);

            moodList = user.getMoodList();

            moods = new ArrayList<>();

            for(int i = 0; i < moodList.getSize(); i++) {
                moods.add(moodList.get(i));
            }

            Collections.sort(moods, new Comparator<Mood>() {
                public int compare(Mood mood1, Mood mood2) {
                    return mood1.getDate().compareTo(mood2.getDate());
                }
            });

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
