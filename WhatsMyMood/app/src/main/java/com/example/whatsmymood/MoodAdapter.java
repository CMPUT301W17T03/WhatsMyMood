package com.example.whatsmymood;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private final Context mContext;

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

        setMoodEmoticon(mood, customView);


        try {
            PhotoController photoController = new PhotoController();
            Bitmap photo = photoController.decodePhoto(mood.getPhoto());

            ImageView image = (ImageView) customView.findViewById(R.id.moodImage);
            image.setImageBitmap(photo);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //emoticon.setImageResource(R.drawable.happiness);
        //image.setImageResource(R.drawable.def_pic_vert);



        ImageView viewLocationButton = (ImageView) customView.findViewById(R.id.locationButton);
        viewLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MapActivity.class);
                ArrayList<Mood> temp = new ArrayList<Mood>();
                temp.add(mood);
                intent.putParcelableArrayListExtra("moods",temp);
                mContext.startActivity(intent);
            }
        });

        authorText.setText(mood.getMoodAuthor());
        moodTypeText.setText(mood.getMoodType());
        moodMessageText.setText(mood.getMoodMsg());
        socialSituationText.setText(mood.getSocialSit());
        Date date = mood.getDate();
        dateText.setText(DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT).format(mood.getDate()));

        if (this.mContext instanceof ProfileActivity) {
            final ImageButton moodButton = (ImageButton) customView.findViewById(R.id.mood_functions);
            moodButton.setVisibility(View.VISIBLE);
            moodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(getContext(), moodButton);
                    popup.getMenuInflater()
                            .inflate(R.menu.mood_functions, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_mood:
                                    dialog = new Dialog(mContext);
                                    dialog.setContentView(R.layout.add_mood_popup);
                                    if (!dialog.isShowing()) {
                                        moodController = new AddMoodController(mContext, dialog);
                                        moodController.preFill(mood);
                                        dialog.show();
                                    } else {
                                        dialog.dismiss();
                                    }
                                    return true;
                                case R.id.delete_mood:
                                    UserAccount user = CurrentUser.getInstance().getCurrentUser();
                                    user.getMoodList().removeMood(mood);

                                    ThemeController.notifyThemeChange((Activity)mContext);

                                    ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                                    updateUser.execute(user);
                                    return true;
                            }
                            return true;
                        }
                    });
                }
            });
        }
        return customView;
    }

    private void setMoodEmoticon(Mood mood, View customView) {
        ImageView emoticon = (ImageView) customView.findViewById(R.id.moodEmoticon);
        switch (mood.getMoodType()) {
            case "Happiness":
                emoticon.setImageResource(R.drawable.happiness);
                break;
            case "Sadness":
                emoticon.setImageResource(R.drawable.sadness);
                break;
            case "Anger":
                emoticon.setImageResource(R.drawable.anger);
                break;
            case "Confusion":
                emoticon.setImageResource(R.drawable.confused);
                break;
            case "Disgusted":
                emoticon.setImageResource(R.drawable.disgusted);
                break;
            case "Scared":
                emoticon.setImageResource(R.drawable.scared);
                break;
            case "Shame":
                emoticon.setImageResource(R.drawable.shame);
                break;
            case "Surprise":
                emoticon.setImageResource(R.drawable.surprise);
                break;

        }
    }

}