package com.example.whatsmymood;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The type Request adapter.
 * Created By ejtang 07/03/2017
 */
public class RequestAdapter extends ArrayAdapter<String> {
    private final CurrentUser current = CurrentUser.getInstance();
    private UserAccount mUser;
    private UserAccount mRequestUser;

    private Follows follows;
    private Follows requestFollows;

    private ListView followersList;
    private ListView followingList;
    private ListView requestsList;

    private ArrayList<String> mUsernames;
    private final Context mContext;

    /**
     * Instantiates a new Request adapter.
     */
    public RequestAdapter(ArrayList<String> usernames, Context context) {
        super(context, R.layout.request_adapter, usernames);
        this.mUsernames = usernames;
        this.mContext = context;
    }

    /**
     * Gets the view for follow requests
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    //TODO create accept and delete buttons in custom adapter
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater requestsInflator = LayoutInflater.from(getContext());
        View customView = requestsInflator.inflate(R.layout.request_adapter, parent, false);

        Button acceptButton = (Button) customView.findViewById(R.id.acceptButton);
        Button declineButton = (Button) customView.findViewById(R.id.declineButton);

        followersList = (ListView) ((Activity) mContext).findViewById(R.id.followersList);
        followingList = (ListView) ((Activity) mContext).findViewById(R.id.followingList);
        requestsList = (ListView) ((Activity) mContext).findViewById(R.id.requestsList);

        final String user = getItem(position);
        TextView usernameText = (TextView) customView.findViewById(R.id.usernameText);
        usernameText.setText(user);

        mUser = current.getCurrentUser();

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData(user);

                follows.addToFollowedBy(user);
                follows.removeFromFollowRequests(user);
                requestFollows.addToFollowing(mUser.getUsername());

                updateData(mUser);
                updateData(mRequestUser);

                setAdapters();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData(user);

                follows.removeFromFollowRequests(user);

                updateData(mUser);

                ArrayAdapter<String> requestsAdapter = new RequestAdapter(follows.getFollowRequestsList(), mContext);
                requestsList.setAdapter(requestsAdapter);
                justifyListViewHeightBasedOnChildren(requestsList);
            }
        });

        return customView;
    }

    private void fetchData(String requestUser) {
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        ElasticSearchUserController.GetUserTask getRequestUserTask = new ElasticSearchUserController.GetUserTask();

        getUserTask.execute(mUser.getUsername());
        getRequestUserTask.execute(requestUser);

        try {
            ArrayList<UserAccount> userList = getUserTask.get();
            ArrayList<UserAccount> requestUserList = getRequestUserTask.get();

            mUser = userList.get(0);
            mRequestUser = requestUserList.get(0);

            follows = mUser.getFollows();
            requestFollows = mRequestUser.getFollows();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateData(UserAccount user) {
        ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
        updateUser.execute(user);
    }

    private void setAdapters() {
        ArrayAdapter<String> followersAdapter = new FollowAdapter(follows.getFollowedByList(), mContext);
        ArrayAdapter<String> followingAdapter = new FollowAdapter(follows.getFollowingList(), mContext);
        ArrayAdapter<String> requestsAdapter = new RequestAdapter(follows.getFollowRequestsList(), mContext);

        followersList.setAdapter(followersAdapter);
        followingList.setAdapter(followingAdapter);
        requestsList.setAdapter(requestsAdapter);

        justifyListViewHeightBasedOnChildren(followersList);
        justifyListViewHeightBasedOnChildren(followingList);
        justifyListViewHeightBasedOnChildren(requestsList);
    }

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

}
