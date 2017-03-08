package com.example.whatsmymood;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by nathan on 07/03/17.
 */

public class AddMoodController {

    private View view;

    private Mood mood;

    private String moodType;
    private String moodAuthor;
    private String moodMsg = null;
    private String location = null;
    private String socialSit = null;
    private String date;


    private AddMoodController(View view, final Dialog dialog) {
        this.view = view;

        Button post = (Button) this.view.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMood();
                dialog.dismiss();
            }
        });
    }

    public void getMood() {

        // TODO: automatically set the author to the current user
        // String moodAuthor =

        Spinner spinner = (Spinner) this.view.findViewById(R.id.select_mood);

        if (!spinner.getSelectedItem().toString().isEmpty()) {
            this.moodType = spinner.getSelectedItem().toString();
        }

        EditText msg = (EditText) this.view.findViewById(R.id.enter_description);

        if (!msg.getText().toString().isEmpty()) {
            this.moodMsg = msg.getText().toString();
        }


        // TODO: Make this an actual location
        // TODO: Handle exception where user does not input a location
        // Possibly find the current location? Or just not put a location
        EditText location = (EditText) this.view.findViewById(R.id.enter_location);

        if (!location.getText().toString().isEmpty()) {
            this.location = location.getText().toString();
        }

        EditText socialSit = (EditText) this.view.findViewById(R.id.enter_tags);

        if (!socialSit.getText().toString().isEmpty()) {
            this.socialSit = socialSit.getText().toString();
        }

        EditText date = (EditText) this.view.findViewById(R.id.enter_date);

        if (!date.getText().toString().isEmpty()) {
            this.date = date.getText().toString();

            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
            check.setLenient(false);

            try {
                Date checkdate = check.parse(this.date);
            } catch(ParseException e) {
                e.printStackTrace();
            }
        }

    }
    /*
    public Mood makeMood() {

        // If the date is null, automatically set the date to the current date
        if (this.date == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());

            this.mood = new Mood(this.moodType, this.moodAuthor, currentDateandTime);
        } else {
            this.mood = new Mood(this.moodType, this.moodAuthor, date);
        }

        mood.setMoodMsg(this.moodMsg);
        mood.setLocation(this.location);


    }*/

}