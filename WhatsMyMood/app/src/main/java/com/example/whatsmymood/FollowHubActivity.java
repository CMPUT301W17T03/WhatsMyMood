package com.example.whatsmymood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private Follows follows;
    private Follows requestFollows;

    private ListView followersList;
    private ListView followingList;
    private ListView requestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow_hub);
        followingList = (ListView) findViewById(R.id.followingList);
        followersList = (ListView) findViewById(R.id.followersList);
        requestsList = (ListView) findViewById(R.id.requestsList);

        TextView followingText = (TextView) findViewById(R.id.following);
        TextView followersText = (TextView) findViewById(R.id.followers);
        TextView requestsText = (TextView) findViewById(R.id.requests);

        final EditText addRequestText = (EditText) findViewById(R.id.body);
        Button addRequest = (Button) findViewById(R.id.add);

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

        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElasticSearchUserController.GetUserTask getRequestUserTask = new ElasticSearchUserController.GetUserTask();
                getRequestUserTask.execute(addRequestText.getText().toString());

                try {
                    UserAccount user = getRequestUserTask.get().get(0);
                    requestFollows = user.getFollows();

                    if (!user.getFollows().isFollowedBy(current.getCurrentUser().getUsername())) {
                        requestFollows.addToFollowRequests(current.getCurrentUser().getUsername());
                        ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                        updateUser.execute(user);
                    } else {
                        addRequestText.setError("Already following User");
                    }
                } catch (ExecutionException | IndexOutOfBoundsException e) {
                    addRequestText.setError("User Doesn't Exist");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
        setAdapters();
    }

    private void setAdapters() {
        ArrayAdapter<String> followersAdapter = new FollowAdapter(follows.getFollowedByList(), this);
        ArrayAdapter<String> followingAdapter = new FollowAdapter(follows.getFollowingList(), this);
        ArrayAdapter<String> requestsAdapter = new RequestAdapter(follows.getFollowRequestsList(), this);

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
    private void fetchData() {
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {

            // This line is the reason why opening the follow hub is slow by the way
            ArrayList<UserAccount> userList = getUserTask.get();


            follows = userList.get(0).getFollows();
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