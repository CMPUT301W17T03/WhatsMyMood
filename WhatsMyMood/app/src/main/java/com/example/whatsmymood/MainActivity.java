package com.example.whatsmymood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView moodList;
    private ArrayList<Mood> moods;
    private ArrayAdapter<Mood> moodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);

        // Instantiate out adapter and set view for viewing moods
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
        // all this data is here for proof of concept
        //TODO: replace this with elastic search and populate moods
        moods.add(new Mood("John","Happy"));
        moods.add(new Mood("John","Sad"));
        moods.add(new Mood("John","Mad"));
        moods.add(new Mood("John","Woot"));
        moods.add(new Mood("John","Happy"));
        moods.add(new Mood("John","Sad"));
        moods.add(new Mood("John","Mad"));
        moods.add(new Mood("John","Woot"));
        moods.add(new Mood("John","Happy"));
        moods.add(new Mood("John","Sad"));
        moods.add(new Mood("John","Mad"));
        moods.add(new Mood("John","Woot"));
        moods.add(new Mood("John","Happy"));
        moods.add(new Mood("John","Sad"));
        moods.add(new Mood("John","Mad"));
        moods.add(new Mood("John","Woot"));
    }


}
