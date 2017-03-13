package com.example.whatsmymood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ejtang on 2017-03-13.
 */

public class MoodAdapter extends ArrayAdapter<Mood> {
    private ArrayList<Mood> moods;
    private Context context;

    /**
     * Instantiates a new Follow adapter.
     */
    public MoodAdapter(ArrayList<Mood> moods, Context context) {
        super(context, R.layout.mood_adapter, moods);
        this.moods = moods;
        this.context = context;
    }

    //TODO: ensure that the information being displayed is how we want it
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater moodInflator = LayoutInflater.from(getContext());
        View customView = moodInflator.inflate(R.layout.mood_adapter, parent, false);

        Mood mood = (Mood) getItem(position);
        TextView authorText = (TextView) customView.findViewById(R.id.author);
        TextView moodTypeText = (TextView) customView.findViewById(R.id.mood);
        TextView moodMessageText = (TextView) customView.findViewById(R.id.moodMessage);
        TextView socialSituationText = (TextView) customView.findViewById(R.id.moodSocialSituation);
        TextView dateText = (TextView) customView.findViewById(R.id.moodDate);

        authorText.setText(mood.getMoodAuthor());
        moodTypeText.setText(mood.getMoodType());
        moodMessageText.setText(mood.getMoodMsg());
        socialSituationText.setText(mood.getSocialSit());
        dateText.setText(mood.getDate().toString());

        return customView;
    }

}
