package com.example.whatsmymood;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Takes user input and converts it into a relevant mood
 */
class AddMoodController{
    // Invalid User Selections
    private boolean DATE_INVALID = false;
    private boolean SELECT_MOOD_INVALID = false;

    private static final int RESULT_OK = -1;

    // Permissions for the camera
    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 1;

    // Activity Result Codes
    private final static int CAPTURE_IMAGE_REQUEST_CODE = 2;

    // Variable for camera permission checks
    private int cameraPermissionCheck;

    // Interface
    private static ImageButton photoButton;

    private final Dialog dialog;
    private final Context context;


    private String moodType;

    private String moodAuthor;

    // Set to null because they are not mandatory
    private String moodMsg = null;
    private String location = null;
    private String socialSit = null;

    private Date date;

    // TODO: Figure out how we're handling photos
    private static String mPhoto;


    /**
     * Passes the dialog and context
     * Sets up the click functionality for
     * the camera button and the post button
     * @param mContext Base context of the activity from main
     * @param mDialog Dialog created in footer handler
     */
    public AddMoodController(Context mContext, Dialog mDialog) {
        this.dialog = mDialog;
        this.context = mContext;

        // Get Access to the Camera

        photoButton = (ImageButton) dialog.findViewById(R.id.load_picture);

        photoButton.setBackgroundResource(R.mipmap.camera);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // http://stackoverflow.com/questions/38980647/i-need-to-get-the-activity-in-order-to-request-permissions
                // March 13th,2017 1:48
                cameraPermissionCheck = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
                if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_ACCESS_CAMERA);
                }
                else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    ((Activity) context).startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                }
            }
        });

        // Sets the mood on post button click

        Button post = (Button) dialog.findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser current = CurrentUser.getInstance();
                moodAuthor = CurrentUser.getInstance().getCurrentUser().getUsername();
                UserAccount user = current.getCurrentUser();
                Mood m = getMood();
                if(m != null) {
                    user.moodList.addMood(getMood());

                    ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                    updateUser.execute(user);

                    dialog.dismiss();
                }
            }
        });
    }


    public static void processResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) intent.getExtras().get("data");


                // TODO: Figure out a way to not create memory leaks from this line
                // Basically photo's work but this line sets the image to the thumbnail of
                // the photo you just took. This is kind of optional, it's only so the user
                // knows that a photo was actually taken
                photoButton.setImageBitmap(photo);

                PhotoController photoController = new PhotoController();

                mPhoto = photoController.encodePhoto(photo);
            }
        }
    }

    /**
     * Main controller actions
     * Takes each input and converts it into
     * their respective variables
     * @return Calls make mood to make a mood object
     */
    private Mood getMood() {

        Spinner spinner = (Spinner) this.dialog.findViewById(R.id.select_mood);

        if (!spinner.getSelectedItem().toString().equals("Select a mood")) {
            this.moodType = spinner.getSelectedItem().toString();
        } else {
            // Set to TRUE if they have no selected an entry
            SELECT_MOOD_INVALID = true;
        }

        EditText msg = (EditText) this.dialog.findViewById(R.id.enter_description);

        if (!msg.getText().toString().isEmpty()) {
            this.moodMsg = msg.getText().toString();
        }

        // TODO: Make this an actual location
        // TODO: Handle exception where user does not input a location/invalid locations
        EditText location = (EditText) this.dialog.findViewById(R.id.enter_location);

        if (!location.getText().toString().isEmpty()) {
            this.location = location.getText().toString();
        }

        EditText socialSit = (EditText) this.dialog.findViewById(R.id.enter_tags);

        if (!socialSit.getText().toString().isEmpty()) {
            this.socialSit = socialSit.getText().toString();
        }

        EditText date = (EditText) this.dialog.findViewById(R.id.enter_date);

        // Checks if the date is empty
        // and parses the date to make sure
        // the date format is correct
        if (!date.getText().toString().isEmpty()) {

            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            check.setLenient(false);

            try {
                this.date = check.parse(date.getText().toString());
            } catch(ParseException e) {
                e.printStackTrace();
                DATE_INVALID = true;
            }
        }

        if (SELECT_MOOD_INVALID) {

            // TODO: Find a better way to output the error
            TextView textview = (TextView) spinner.getSelectedView();
            textview.setError("");
            textview.setTextColor(Color.RED);//just to highlight that this is an error
            textview.setText(R.string.invalid_mood);

            // TODO: Handle invalid mood properly
            // SELECT_MOOD_INVALID is always set to true unless we manually set it to false
            SELECT_MOOD_INVALID = false;
        } else if (DATE_INVALID) {

            date.setError("Invalid Date Inputted (yyyy-MM-DD)");

            // TODO: Handle invalid date properly
            // DATE_INVALID is always set to true unless we manually set it to false
            DATE_INVALID = false;

            // Makes the mood if there are no errors
        } else {

            return makeMood();
        }
        return null;
    }

    /**
     * Creates the mood
     * Automatically creates a date to the current date
     * if there is no date specified
     * @return Returns a mood object
     */
    private Mood makeMood() {
        Mood mood;

        // If the date is null, automatically set the date to the current date
        if (this.date == null) {
            Date newDate = new Date();
            mood = new Mood(this.moodAuthor,this.moodType, newDate);
        } else {
            mood = new Mood(this.moodAuthor,this.moodType, this.date);
        }

        mood.setMoodMsg(this.moodMsg);
        mood.setLocation(this.location);
        mood.setSocialSit(this.socialSit);
        mood.setPhoto(mPhoto);

        return mood;
    }
}