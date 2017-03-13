package com.example.whatsmymood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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

    //TODO create accept and delete buttons in custom adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater peopleInflator = LayoutInflater.from(getContext());
        View customView = peopleInflator.inflate(R.layout.request_adapter, parent, false);

        String user = getItem(position);
        TextView usernameText = (TextView) customView.findViewById(R.id.usernameText);

        usernameText.setText(user);

        return customView;
    }
}
