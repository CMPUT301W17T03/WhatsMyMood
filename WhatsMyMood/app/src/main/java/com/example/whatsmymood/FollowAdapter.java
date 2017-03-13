package com.example.whatsmymood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Follow adapter.
 * Created by ejtang on 7/03/2017
 */
public class FollowAdapter extends ArrayAdapter<String> {
    private ArrayList<String> usernames;
    private Context context;

    /**
     * Instantiates a new Follow adapter.
     */
    public FollowAdapter(ArrayList<String> usernames, Context context) {
        super(context, R.layout.follow_adapter, usernames);
        this.usernames = usernames;
        this.context = context;
    }

    //TODO possibly adjust custom adapter to click on the username to get to their profile
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater peopleInflator = LayoutInflater.from(getContext());
        View customView = peopleInflator.inflate(R.layout.follow_adapter, parent, false);

        String user = getItem(position);
        TextView usernameText = (TextView) customView.findViewById(R.id.usernameText);
        usernameText.setText(user);

        return customView;
    }

}
