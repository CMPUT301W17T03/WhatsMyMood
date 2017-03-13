package com.example.whatsmymood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.attr.id;

/**
 * The type Follow hub activity.
 * Created by ejtang on 7/03/2017
 */
public class FollowHubActivity extends AppCompatActivity {
    private TextView followersText;
    private ListView followersList;
    private ArrayList<String> followers;
    private ArrayAdapter<String> followersAdapter;

    private TextView followingText;
    private ListView followingList;
    private ArrayList<String> following;
    private ArrayAdapter<String> followingAdapter;

    private TextView requestsText;
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

        followersText = (TextView) findViewById(R.id.followers);
        followingText = (TextView) findViewById(R.id.following);
        requestsText = (TextView) findViewById(R.id.requests);

        followersText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(followersList);
            }
        });

        followingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(followingList);
            }
        });

        requestsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(requestsList);
            }
        });



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

        justifyListViewHeightBasedOnChildren(followersList);
        justifyListViewHeightBasedOnChildren(followingList);
        justifyListViewHeightBasedOnChildren(requestsList);

    }

    /**
     * Fetch data.
     */
//TODO grab the information from elastic search and place it into lists
    protected void fetchData() {
        // currently using static data to prove concept works



        Log.d("fetch","Fetching data...");
        followers.add("John Doe");
        followers.add("Jane Doe");
        followers.add("Malcolm");
        followers.add("John Doe");
        followers.add("Jane Doe");
        followers.add("Malcolm");
        followers.add("John Doe");
        followers.add("Jane Doe");
        followers.add("Malcolm");
        followers.add("John Doe");
        followers.add("Jane Doe");
        followers.add("Malcolm");
        followers.add("John Doe");
        followers.add("Jane Doe");
        followers.add("Malcolm");

        following.add("person1");
        following.add("person2");
        following.add("person3");
        following.add("person1");
        following.add("person2");
        following.add("person3");
        following.add("person1");
        following.add("person2");
        following.add("person3");
        following.add("person1");
        following.add("person2");
        following.add("person3");
        following.add("person1");
        following.add("person2");
        following.add("person3");

        requests.add("person4");
        requests.add("person5");
        requests.add("person6");
    }


    /**
     * Justify list view height based on children.
     *
     * This function was taken from
     * http://stackoverflow.com/questions/4338185/how-to-get-a-non-scrollable-listview/27818748#27818748
     * the website was view on March 13, 2017
     *
     * @param listView the list view
     */
    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private void toggleVisibility (ListView v) {
        if (v.isShown()) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
    }

}