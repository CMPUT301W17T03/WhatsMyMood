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
 * The type Mood adapter.
 * Created by ejtang
 *
 * An adapter that will adapt the view to display mood information about the mood that is to
 * be adapted
 */
class MoodAdapter extends ArrayAdapter<Mood> {
    private final ArrayList<Mood> moods;
    private final Context context;

    /**
     * Instantiates a new Follow adapter.
     *
     * @param moods   the moods
     * @param context the context
     */
    public MoodAdapter(ArrayList<Mood> moods, Context context) {
        super(context, R.layout.mood_adapter, moods);
        this.moods = moods;
        this.context = context;
    }

    /**
     * Displays the recent mood of the people you are following
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    //TODO: ensure that the information being displayed is how we want it
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater moodInflator = LayoutInflater.from(getContext());
        View customView = moodInflator.inflate(R.layout.mood_adapter, parent, false);

        Mood mood = getItem(position);
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