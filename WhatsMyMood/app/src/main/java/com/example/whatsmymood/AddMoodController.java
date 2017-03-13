package com.example.whatsmymood;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.games.video.Videos;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by nathan on 07/03/17.
 */


/**
 *  PLEASE NOTE I HAVE NOT TESTED THIS YET - NATHAN
 */
public class AddMoodController extends AppCompatActivity{
    private boolean DATE_INVALID = false;
    private boolean SELECT_MOOD_INVALID = false;

    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 1;

    private final static int CAPTURE_IMAGE_REQUEST_CODE = 2;
    private final static int CONFIRM = 3;

    private int cameraPermissionCheck;

    private Dialog dialog;
    private Context mContext;

    private Mood mood;

    private String moodType;

    private String moodAuthor;

    private String moodMsg = null;
    private String location = null;
    private String socialSit = null;
    private Date date;

    // TODO: Figure out how we're handling photos
    private String photo;

    public AddMoodController(final Context mContext, Dialog d, View view) {
        this.dialog = d;
        this.mContext = mContext;

        /**
         * Get access to the camera in android on user click
         */

        Button photoButton = (Button) dialog.findViewById(R.id.load_picture);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // http://stackoverflow.com/questions/38980647/i-need-to-get-the-activity-in-order-to-request-permissions March 13th,2017 1:48
                cameraPermissionCheck = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA);
                if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_ACCESS_CAMERA);
                }
                else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    ((Activity) mContext).startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                    Log.d("tag", "finished");
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("tag", "it started");
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("tag", "1");
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            Log.d("tag", "2");
            Log.d("tag", String.valueOf(resultCode));
            Log.d("tag", "3");
            if (resultCode == CONFIRM) {
                Log.d("tag", "hi");
                Bitmap photo = (Bitmap) intent.getExtras().get("data");

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                this.photo = encodedImage;
                Log.d("tag", this.photo);
            }
        }
    }

    public Mood getMood() {

        Spinner spinner = (Spinner) this.dialog.findViewById(R.id.select_mood);

        if (!spinner.getSelectedItem().toString().equals("Select a mood")) {
            this.moodType = spinner.getSelectedItem().toString();
        } else {
            SELECT_MOOD_INVALID = true;
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

        if (SELECT_MOOD_INVALID) {

            // TODO: Find a better way to output the error
            Spinner mSpinner = (Spinner) this.dialog.findViewById(R.id.select_mood);
            TextView textview = (TextView) mSpinner.getSelectedView();
            textview.setError("");
            textview.setTextColor(Color.RED);//just to highlight that this is an error
            textview.setText("Invalid Mood Selected");

            // TODO: Handle invalid mood properly
            // SELECT_MOOD_INVALID is always set to true unless we manually set it to false
            SELECT_MOOD_INVALID = false;
        } else if (DATE_INVALID) {
            TextView textview = (TextView) this.dialog.findViewById(R.id.enter_date);
            textview.setError("Invalid Date Inputed");

            // TODO: Handle invalid date properly
            // DATE_INVALID is always set to true unless we manually set it to false
            DATE_INVALID = false;
        } else {
            return makeMood();
        }
        return null;
    }

    public Mood makeMood() {

        // If the date is null, automatically set the date to the current date
        if (this.date == null) {
            Date newDate = new Date();
            this.mood = new Mood(this.moodAuthor,this.moodType, newDate);
        } else {
            this.mood = new Mood(this.moodAuthor,this.moodType, this.date);
        }

        mood.setMoodMsg(this.moodMsg);
        mood.setLocation(this.location);
        mood.setSocialSit(this.socialSit);
        mood.setPhoto(this.photo);

        return mood;
    }
}