package com.example.whatsmymood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
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

    private View v;

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


    private AddMoodController(View v) {
        this.v = v;
    }

    public void getMood() {

        // TODO: Automatically set the author to the current user

        Spinner spinner = (Spinner) this.v.findViewById(R.id.select_mood);

        if (!spinner.getSelectedItem().toString().isEmpty()) {
            this.moodType = spinner.getSelectedItem().toString();
        }

        EditText msg = (EditText) this.v.findViewById(R.id.enter_description);

        if (!msg.getText().toString().isEmpty()) {
            this.moodMsg = msg.getText().toString();
        }

        EditText location = (EditText) this.v.findViewById(R.id.enter_location);

        if (!location.getText().toString().isEmpty()) {
            this.location = location.getText().toString();
        }

        EditText socialSit = (EditText) this.v.findViewById(R.id.enter_tags);

        if (!socialSit.getText().toString().isEmpty()) {
            this.socialSit = socialSit.getText().toString();
        }

        EditText date = (EditText) this.v.findViewById(R.id.enter_date);

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
            TextView textview = (TextView) this.v.findViewById(R.id.invalid);
            textview.setText("Invalid Date");
        } else {
            makeMood();
        }

    }

    public Mood makeMood() {

        // If the date is null, automatically set the date to the current date
        if (this.date == null) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = new Date();

            try {
                Date currentDateandTime = sdf.parse(newDate.toString());
                this.mood = new Mood(this.moodType, this.moodAuthor, currentDateandTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

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