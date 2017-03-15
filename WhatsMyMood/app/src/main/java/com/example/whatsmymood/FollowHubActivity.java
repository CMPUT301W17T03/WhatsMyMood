package com.example.whatsmymood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The type Follow hub activity.
 * Created by ejtang on 7/03/2017
 */
public class FollowHubActivity extends AppCompatActivity {
    private final CurrentUser current = CurrentUser.getInstance();

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

        followers = new ArrayList<>();
        following = new ArrayList<>();
        requests = new ArrayList<>();

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
    private void fetchData() {
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {
            ArrayList<UserAccount> userList = getUserTask.get();

            followers = userList.get(0).getFollows().getFollowedByList();
            following = userList.get(0).getFollows().getFollowingList();
            requests = userList.get(0).getFollows().getFollowRequestsList();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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
    private void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
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