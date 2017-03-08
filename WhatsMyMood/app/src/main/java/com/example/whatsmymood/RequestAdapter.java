package com.example.whatsmymood;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * The type Request adapter.
 * Created By ejtang 07/03/2017
 */
public class RequestAdapter extends ArrayAdapter<String> {
    private ArrayList<String> usernames;
    private Context context;

    /**
     * Instantiates a new Request adapter.
     */
    public RequestAdapter(ArrayList<String> usernames, Context context) {
        super(context, R.layout.request_adapter, usernames);
        this.usernames = usernames;
        this.context = context;
    }
    //TODO add a custom adapter that will allow us to accept or decline on each instance of it
}
