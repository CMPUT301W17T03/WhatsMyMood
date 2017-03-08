package com.example.whatsmymood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The type Follow hub activity.
 * Created by ejtang on 7/03/2017
 */
public class FollowHubActivity extends AppCompatActivity {
    private ScrollView followersList;
    private ArrayList<String> followers;
    private ArrayAdapter<String> followersAdapter;

    private ScrollView followingList;
    private ArrayList<String> following;
    private ArrayAdapter<String> followingAdapter;

    private ScrollView requestsList;
    private ArrayList<String> requests;
    private ArrayAdapter<String> requestsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_hub);
    }

    protected void onStart() {
        super.onStart();
        followersAdapter = new FollowAdapter(followers, this);
        followingAdapter = new FollowAdapter(following, this);
        requestsAdapter = new RequestAdapter(requests, this);

    }

}
