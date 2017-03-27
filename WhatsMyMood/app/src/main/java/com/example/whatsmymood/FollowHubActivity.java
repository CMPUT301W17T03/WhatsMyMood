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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The type Follow hub activity.
 * Created by ejtang on 7/03/2017
 *
 *
 *
 * @author Mtfische
 *
 * This is an activity that will allow the user to view who they are following
 * who is following them, and who is requesting to follow the user.
 */
public class FollowHubActivity extends AppCompatActivity {
    private final CurrentUser current = CurrentUser.getInstance();

    //private Relations requestRelations;

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

        setTouchListners();
        setAdapters();

        final EditText addRequestText = (EditText) findViewById(R.id.body);
        Button addRequest = (Button) findViewById(R.id.add);

        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElasticSearchUserController.GetUserTask getRequestUserTask = new ElasticSearchUserController.GetUserTask();
                getRequestUserTask.execute(addRequestText.getText().toString());

                try {
                    UserAccount user = getRequestUserTask.get().get(0);

                    if (!user.relations.isFollowedBy(current.getCurrentUser().getUsername())) {
                        if(!user.relations.hasRequests(current.getCurrentUser().getUsername())) {
                            user.relations.addToFollowRequests(current.getCurrentUser().getUsername());

                            ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                            updateUser.execute(user);

                            //Todo this will have to be altered with offline
                            String successString = "Request Sent";
                            Toast.makeText(getBaseContext(),successString, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            addRequestText.setError("Already requested to follow user");
                        }
                    } else {
                        addRequestText.setError("Already following user");
                    }


                } catch (ExecutionException | IndexOutOfBoundsException e) {
                    addRequestText.setError("User Doesn't Exist");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * To be called when we wish to refresh the lists.
     */
    private void refresh(){
        ((ArrayAdapter)followersList.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter)followingList.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter)requestsList.getAdapter()).notifyDataSetChanged();
    }
    private void setAdapters() {
        ArrayAdapter<String> followersAdapter = new FollowAdapter(current.getCurrentUser().relations.getFollowedByList(), this);
        ArrayAdapter<String> followingAdapter = new FollowAdapter(current.getCurrentUser().relations.getFollowingList(), this);
        ArrayAdapter<String> requestsAdapter = new RequestAdapter(current.getCurrentUser().relations.getFollowRequestsList(), this);


        followersList.setAdapter(followersAdapter);
        followingList.setAdapter(followingAdapter);
        requestsList.setAdapter(requestsAdapter);

        justifyListViewHeightBasedOnChildren(followersList);
        justifyListViewHeightBasedOnChildren(followingList);
        justifyListViewHeightBasedOnChildren(requestsList);
    }

    private void setTouchListners() {
        TextView followingText = (TextView) findViewById(R.id.following);
        TextView followersText = (TextView) findViewById(R.id.followers);
        TextView requestsText = (TextView) findViewById(R.id.requests);

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
    /**
     * Justify list view height based on children.
     *
     * This function was taken from
     * http://stackoverflow.com/questions/4338185/how-to-get-a-non-scrollable-listview/27818748#27818748
     * the website was view on March 13, 2017
     *
     * @param listView the list view
     */
    //TODO this function is to be removed upon change of xml
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