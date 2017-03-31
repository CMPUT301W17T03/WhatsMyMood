package com.example.whatsmymood;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Mood adapter.
 * Created by ejtang
 *
 * An adapter that will adapt the view to display mood information about the mood that is to
 * be adapted
 */
class MoodAdapter extends ArrayAdapter<Mood> {
    private AddMoodController moodController;
    private Dialog dialog;
    private Context mContext;

    /**
     * Instantiates a new Mood adapter.
     *
     * @param moods   the moods
     * @param context the context
     */
    public MoodAdapter(ArrayList<Mood> moods, Context context) {
        super(context, R.layout.mood_adapter, moods);
        this.mContext = context;
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

        final Mood mood = getItem(position);
        TextView authorText = (TextView) customView.findViewById(R.id.author);
        TextView moodTypeText = (TextView) customView.findViewById(R.id.mood);
        TextView moodMessageText = (TextView) customView.findViewById(R.id.moodMessage);
        TextView socialSituationText = (TextView) customView.findViewById(R.id.moodSocialSituation);
        TextView dateText = (TextView) customView.findViewById(R.id.moodDate);

        //ImageView emoticon = (ImageView) customView.findViewById(R.id.moodEmoticon);
        //ImageView image = (ImageView) customView.findViewById(R.id.moodImage);

        //emoticon.setImageResource(R.drawable.def_emoticon);
        //image.setImageResource(R.drawable.def_pic_vert);

        authorText.setText(mood.getMoodAuthor());
        moodTypeText.setText(mood.getMoodType());
        moodMessageText.setText(mood.getMoodMsg());
        socialSituationText.setText(mood.getSocialSit());
        Date date = mood.getDate();
        dateText.setText(DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT).format(mood.getDate()));

        if (this.mContext instanceof ProfileActivity) {
            RelativeLayout moodClick = (RelativeLayout) customView.findViewById(R.id.edit_mood);
            moodClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.add_mood_popup);
                    if (!dialog.isShowing()) {
                        moodController = new AddMoodController(mContext, dialog);
                        moodController.preFill(mood);
                        dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }

        return customView;
    }

}