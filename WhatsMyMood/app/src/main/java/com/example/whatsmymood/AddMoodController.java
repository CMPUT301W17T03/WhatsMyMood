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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by nathan on 07/03/17.
 */

public class AddMoodController {
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 2;
    public static final int CONFIRM = 3;

    private static boolean dateInvalid = false;
    private static boolean locationInvalid = false;

    private Dialog dialog;
    private Context mContext;

    private Mood mood;

    private String moodType;

    // TODO: Automatically set the author to the current user
    private String moodAuthor = "Nathan";

    private String moodMsg = null;
    private String location = null;
    private String socialSit = null;
    private String date;
    private Bitmap photo;


    public AddMoodController(final Context mContext, Dialog d) {
        this.dialog = d;
        this.mContext = mContext;

        /**
         * Get access to the camera in android on user click
         */
        Button photoButton = (Button) dialog.findViewById(R.id.load_picture);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                ((Activity) mContext).startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        Button post = (Button) dialog.findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser current = CurrentUser.getInstance();
                current.setCurrentUser(new UserAccount("username","password"));
                UserAccount user = current.getCurrentUser();
                Mood m = getMood();
                Log.d("tag", m.toString());
                if(m != null) {
                    user.moodList.addMood(getMood());
                }
                Log.d("tag", user.toString());
                dialog.dismiss();
                //TODO implement the iohandler to update server
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == CONFIRM) {
                Bitmap photo = (Bitmap) intent.getExtras().get("data");
                this.photo = photo;
            }
        }
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
        }


        // TODO: Make this an actual location
        // TODO: Handle exception where user does not input a location/invalid locations
        // Possibly find the current location? Or just not put a location
        EditText location = (EditText) this.dialog.findViewById(R.id.enter_location);

        if (!location.getText().toString().isEmpty()) {
            this.location = location.getText().toString();
        } else { }

        EditText socialSit = (EditText) this.dialog.findViewById(R.id.enter_tags);

        if (!socialSit.getText().toString().isEmpty()) {
            this.socialSit = socialSit.getText().toString();
        }

        EditText date = (EditText) this.dialog.findViewById(R.id.enter_date);


        if (!date.getText().toString().isEmpty()) {
            this.date = date.getText().toString();

            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
            check.setLenient(false);

            try {
                Date checkdate = check.parse(this.date);
            } catch(ParseException e) {
                e.printStackTrace();
                dateInvalid = true;
            }
        }

        if (dateInvalid) {
            TextView textview = (TextView) this.dialog.findViewById(R.id.invalid);
            textview.setText("Invalid Date");
        } else if (locationInvalid) {
            TextView textview = (TextView) this.dialog.findViewById(R.id.invalid);
            textview.setText("Location not found");
        } else {
            return makeMood();
        }
        return null;
    }

    private Mood makeMood() {

        // If the date is null, automatically set the date to the current date
        if (this.date == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());

            this.mood = new Mood(this.moodType, this.moodAuthor);
        } else {
            this.mood = new Mood(this.moodType, this.moodAuthor);
        }

        mood.setMoodMsg(this.moodMsg);
        mood.setLocation(this.location);
        mood.setSocialSit(this.socialSit);
        mood.setPhoto(this.photo);

        return mood;
    }
}