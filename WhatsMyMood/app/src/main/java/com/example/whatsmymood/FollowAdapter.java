package com.example.whatsmymood;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
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
        super(context, R.layout.activity_follow_adapter, usernames);
        this.usernames = usernames;
        this.context = context;
    }


}
