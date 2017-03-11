package com.example.whatsmymood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.attr.id;

/**
 * The type Follow hub activity.
 * Created by ejtang on 7/03/2017
 */
public class FollowHubActivity extends AppCompatActivity {
    private ListView followersList;
    private ArrayList<String> followers;
    private ArrayAdapter<String> followersAdapter;

    private ListView followingList;
    private ArrayList<String> following;
    private ArrayAdapter<String> followingAdapter;

    private ListView requestsList;
    private ArrayList<String> requests;
    private ArrayAdapter<String> requestsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        followers = new ArrayList<String>();
        following = new ArrayList<String>();
        requests = new ArrayList<String>();

        setContentView(R.layout.activity_follow_hub);
        followersList = (ListView) findViewById(R.id.followersList);
        followingList = (ListView) findViewById(R.id.followingList);
        requestsList = (ListView) findViewById(R.id.requestsList);

    }

    protected void onStart() {
        super.onStart();
        followersAdapter = new FollowAdapter(followers, this);
        followingAdapter = new FollowAdapter(following, this);
        requestsAdapter = new RequestAdapter(requests, this);

        followersList.setAdapter(followersAdapter);
        followingList.setAdapter(followingAdapter);
        requestsList.setAdapter(requestsAdapter);

    }

    //TODO grab the information and place add it to our arraylists

}
