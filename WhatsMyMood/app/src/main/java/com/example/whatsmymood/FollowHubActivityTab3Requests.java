package com.example.whatsmymood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by ejtang on 2017-03-29.
 *
 * Referenced off of https://www.youtube.com/watch?v=00LLd7qr9sA for ideas and what to do
 * to get our tabs working
 * Obtained Mar 29, 2017
 */

public class FollowHubActivityTab3Requests extends Fragment {

    private final CurrentUser current = CurrentUser.getInstance();

    private ListView requestsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_follow_hub_requests, container, false);
        requestsList = (ListView) rootView.findViewById(R.id.requestsList);

        ArrayAdapter<String> requestAdapter = new RequestAdapter(
                current.getCurrentUser().relations.getFollowRequestsList(), this.getContext());
        requestsList.setAdapter(requestAdapter);
        return rootView;
    }

    public void refresh() {
        ((ArrayAdapter)requestsList.getAdapter()).notifyDataSetChanged();
        Log.d("refresh","refreshed list!!");
    }
}
