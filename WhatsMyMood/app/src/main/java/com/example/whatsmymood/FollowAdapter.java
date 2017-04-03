package com.example.whatsmymood;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapts list of users to be viewed in the follow hub
 * @author ejtang
 * @version 1.0, 7/03/2017
 */
class FollowAdapter extends ArrayAdapter<String> {
    private final Context mContext;

    /**
     * Instantiates a new Follow adapter.
     */
    public FollowAdapter(ArrayList<String> usernames, Context context) {
        super(context, R.layout.follow_adapter, usernames);
        this.mContext = context;
    }

    /**
     * Sets the view for the profile of the user
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    //TODO possibly adjust custom adapter to click on the username to get to their profile
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater followInflator = LayoutInflater.from(getContext());
        View customView = followInflator.inflate(R.layout.follow_adapter, parent, false);

        String user = getItem(position);
        TextView usernameText = (TextView) customView.findViewById(R.id.usernameText);
        usernameText.setText(user);

        return customView;
    }

}
