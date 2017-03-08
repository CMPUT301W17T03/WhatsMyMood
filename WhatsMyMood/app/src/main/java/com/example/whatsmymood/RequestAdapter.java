package com.example.whatsmymood;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        super(context, R.layout.activity_request_adapter, usernames);
        this.usernames = usernames;
        this.context = context;
    }
}
