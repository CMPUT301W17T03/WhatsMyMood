package com.example.whatsmymood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.R.attr.onClick;

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
        LayoutInflater requestsInflator = LayoutInflater.from(getContext());
        View customView = requestsInflator.inflate(R.layout.request_adapter, parent, false);

        Button acceptButton = (Button) customView.findViewById(R.id.acceptButton);
        Button declineButton = (Button) customView.findViewById(R.id.declineButton);

        String user = getItem(position);
        TextView usernameText = (TextView) customView.findViewById(R.id.usernameText);
        usernameText.setText(user);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add the user to the followers list and remove from requests list
                Log.d("accept","accept button tapped");
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: remove user from requests list
                Log.d("decline","decline button tapped");
            }
        });

        return customView;
    }
}
