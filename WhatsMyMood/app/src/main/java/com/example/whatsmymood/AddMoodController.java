package com.example.whatsmymood;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.util.Log;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by nathan on 07/03/17.
 */


/**
 *  PLEASE NOTE I HAVE NOT TESTED THIS YET - NATHAN
 */
public class AddMoodController {
    private static boolean DATE_INVALID = false;

    private Dialog dialog;
    private Context mContext;

    private Mood mood;

    private String moodType;

    // TODO: Automatically set the author to the current user
    private String moodAuthor = "Nathan";

    private String moodMsg = null;
    private String location = null;
    private String socialSit = null;
    private Date date;

    // TODO: Figure out how we're handling photos
    private Bitmap photo;

    public AddMoodController(final Context mContext, Dialog d) {
        this.dialog = d;
        this.mContext = mContext;

        Button post = (Button) dialog.findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser current = CurrentUser.getInstance();
                current.setCurrentUser(new UserAccount("username","password"));
                UserAccount user = current.getCurrentUser();
                Mood m = getMood();
                if(m != null) {
                    user.moodList.addMood(getMood());

                    ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                    updateUser.execute(user);

                    dialog.dismiss();
                }
                //TODO implement the iohandler to update server
            }
        });
    }

    public Mood getMood() {

        // TODO: Automatically set the author to the current user

        Spinner spinner = (Spinner) this.dialog.findViewById(R.id.select_mood);

        if (!spinner.getSelectedItem().toString().isEmpty()) {
            this.moodType = spinner.getSelectedItem().toString();
        }

        EditText msg = (EditText) this.dialog.findViewById(R.id.enter_description);

        if (!msg.getText().toString().isEmpty()) {
            this.moodMsg = msg.getText().toString();
            Log.d("tagg", this.moodMsg);
        }

        // TODO: Make this an actual location
        // TODO: Handle exception where user does not input a location/invalid locations
        // Possibly find the current location? Or just not put a location
        EditText location = (EditText) this.dialog.findViewById(R.id.enter_location);

        if (!location.getText().toString().isEmpty()) {
            this.location = location.getText().toString();
        }

        EditText socialSit = (EditText) this.dialog.findViewById(R.id.enter_tags);

        if (!socialSit.getText().toString().isEmpty()) {
            this.socialSit = socialSit.getText().toString();
        }

        EditText date = (EditText) this.dialog.findViewById(R.id.enter_date);

        if (!date.getText().toString().isEmpty()) {

            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
            check.setLenient(false);

            try {
                Date moodDate = check.parse(date.getText().toString());
                this.date = moodDate;
            } catch(ParseException e) {
                e.printStackTrace();
                DATE_INVALID = true;
            }
        }

        if (DATE_INVALID) {
            TextView textview = (TextView) this.dialog.findViewById(R.id.enter_date);
            textview.setError("Invalid Date");
        } else {
            return makeMood();
        }
        return null;
    }

    public Mood makeMood() {

        // If the date is null, automatically set the date to the current date
        if (this.date == null) {
            Date newDate = new Date();
            this.mood = new Mood(this.moodType, this.moodAuthor, newDate);
        } else {
            this.mood = new Mood(this.moodType, this.moodAuthor, this.date);
        }

        mood.setMoodMsg(this.moodMsg);
        mood.setLocation(this.location);
        mood.setSocialSit(this.socialSit);
        mood.setPhoto(this.photo);

        return mood;
    }
}