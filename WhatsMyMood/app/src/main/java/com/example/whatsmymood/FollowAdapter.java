package com.example.whatsmymood;

import android.content.Context;
import android.widget.ArrayAdapter;

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


}
