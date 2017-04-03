package com.example.whatsmymood;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
    private UserAccount fetchedUser;

    private final Context mContext;
    private ListView followersList;

    /**
     * Instantiates a new Request adapter.
     */
    public RequestAdapter(ArrayList<String> names, Context context) {
        super(context, R.layout.request_adapter, names);
        this.mContext = context;

        followersList = (ListView) ((Activity) mContext).findViewById(R.id.followersList);
    }

    /**
     * Gets the view for follow requests
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater requestsInflator = LayoutInflater.from(getContext());
        View customView = requestsInflator.inflate(R.layout.request_adapter, parent, false);
;
        Button acceptButton = (Button) customView.findViewById(R.id.acceptButton);
        Button declineButton = (Button) customView.findViewById(R.id.declineButton);

        final String user = getItem(position);
        TextView usernameText = (TextView) customView.findViewById(R.id.usernameText);
        usernameText.setText(user);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current.getCurrentUser().relations.addToFollowedBy(user);
                current.getCurrentUser().relations.removeFromFollowRequests(user);
                ((ArrayAdapter)followersList.getAdapter()).notifyDataSetChanged();
                notifyDataSetChanged();

                fetchData(user);
                fetchedUser.relations.addToFollowing(current.getCurrentUser().getUsername());
                updateData(current.getCurrentUser());
                updateData(fetchedUser);

                ((ArrayAdapter)followersList.getAdapter()).notifyDataSetChanged();
                notifyDataSetChanged();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.getCurrentUser().relations.removeFromFollowRequests(user);
                notifyDataSetChanged();

                fetchData(current.getCurrentUser().getUsername());

                current.setCurrentUser(fetchedUser);
                updateData(current.getCurrentUser());
            }
        });

        return customView;
    }

    private void fetchData(String requestUser) {
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        Log.d("fetch",requestUser);
        getUserTask.execute(requestUser);

        try {
            fetchedUser = getUserTask.get().get(0);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateData(UserAccount user) {
        ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
        updateUser.execute(user);
    }

    //TODO remove this function and change xml to different format
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
