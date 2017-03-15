package com.example.whatsmymood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
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

        //TODO Fecth data periodically e.i. observer/observable
        fetchData();
        followersAdapter = new FollowAdapter(followers, this);
        followingAdapter = new FollowAdapter(following, this);
        requestsAdapter = new RequestAdapter(requests, this);

        followersList.setAdapter(followersAdapter);
        followingList.setAdapter(followingAdapter);
        requestsList.setAdapter(requestsAdapter);

    }

    //TODO grab the information from elastic search and place it into lists
    protected void fetchData() {
        // currently using static data to prove concept works

        UserAccount currentUser = CurrentUser.getInstance().getCurrentUser();

        followers = currentUser.getFollows().getFollowedByList();
//        Log.d("fetch","Fetching data...");
//        followers.add("John Doe");
//        followers.add("Jane Doe");
//        followers.add("Malcolm");

        following = currentUser.getFollows().getFollowingList();
//        following.add("person1");
//        following.add("person2");
//        following.add("person3");

        requests = currentUser.getFollows().getFollowRequestsList();

//        requests.add("person4");
//        requests.add("person5");
//        requests.add("person6");

    }

}