package com.example.whatsmymood;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.security.AccessController.getContext;

/**
 * Takes user input and converts it into a relevant mood
 */
class AddMoodController{
    private CurrentUser current = CurrentUser.getInstance();
    private UserAccount user;

    // Invalid User Selections
    private boolean DATE_INVALID = false;
    private boolean SELECT_MOOD_INVALID = false;
    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;

    private static final int RESULT_OK = -1;

    // Permissions for the camera
    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 1;

    // Activity Result Codes
    private final static int CAPTURE_IMAGE_REQUEST_CODE = 2;

    // Variable for camera permission checks
    private int cameraPermissionCheck;

    private static ImageButton photoButton;

    private final Dialog dialog;
    private final Context context;

    private String moodType;
    private String moodAuthor;
    private String moodMsg = null;
    private LatLng location = null;
    private String socialSit = null;
    private Date date;

    // TODO: Make this nonstatic
    private static String mPhoto;

    // Dialog Layouts
    private Spinner spinner;
    private EditText editMoodMsg;
    private EditText editSocialSit;
    private EditText editDate;

    private Mood mood;

    /**
     * Passes the dialog and context
     * Sets up the click functionality for
     * the camera button and the post button
     * @param mContext Base context of the activity from main
     * @param mDialog Dialog created in footer handler
     */
    public AddMoodController(final Context mContext, Dialog mDialog) {
        this.dialog = mDialog;
        this.context = mContext;

        this.spinner = (Spinner) this.dialog.findViewById(R.id.select_mood);
        this.editMoodMsg = (EditText) this.dialog.findViewById(R.id.enter_description);
        this.editSocialSit = (EditText) this.dialog.findViewById(R.id.enter_tags);
        this.editDate = (EditText) this.dialog.findViewById(R.id.enter_date);

        // Get Access to the Camera
        photoButton = (ImageButton) dialog.findViewById(R.id.load_picture);

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
                moodAuthor = current.getCurrentUser().getUsername();
                Mood mood = getMood();

                if (!(context instanceof ProfileActivity)) {
                    user = current.getCurrentUser();
                    if (mood != null) {
                        user.moodList.addMood(mood);

                        ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                        updateUser.execute(user);

                        dialog.dismiss();
                    }
                }
                else {
                    if (mood != null) {
                        ListView moodListView = (ListView) ((ProfileActivity) context).findViewById(R.id.moodListView);
                        ((ArrayAdapter) moodListView.getAdapter()).notifyDataSetChanged();

                        ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                        updateUser.execute(user);

                        dialog.dismiss();
                    }
                }
            }
        });

        Button addLocation = (Button) dialog.findViewById(R.id.enter_location);

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddLocationActivity.class);
                ((Activity) context).startActivityForResult(intent,SECOND_ACTIVITY_RESULT_CODE);
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

    public void preFill(Mood mood) {
        // Gets the mood for updating
        this.user = current.getCurrentUser();
        int index = user.getMoodList().getIndex(mood);
        this.mood = current.getCurrentUser().getMoodList().get(index);

        // http://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
        // March 30th, 2017
        String compareMood = this.mood.getMoodType();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.context, R.array.mood_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        int position = adapter.getPosition(compareMood);
        this.spinner.setSelection(position);

        try {
            this.editMoodMsg.setText(this.mood.getMoodMsg());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            this.editSocialSit.setText(this.mood.getSocialSit());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.editDate.setText(dateFormat.format(this.mood.getDate()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main controller actions
     * Takes each input and converts it into
     * their respective variables
     * @return Calls make mood to make a mood object
     */
    private Mood getMood() {

        if (!spinner.getSelectedItem().toString().equals("Select a mood")) {
            this.moodType = spinner.getSelectedItem().toString();
        } else {
            SELECT_MOOD_INVALID = true;
        }

        if (!editMoodMsg.getText().toString().isEmpty()) {
            this.moodMsg = editMoodMsg.getText().toString();
        }

        // TODO: Make this an actual location
        // TODO: Handle exception where user does not input a location/invalid locations

        /*EditText location = (EditText) this.dialog.findViewById(R.id.enter_location);

        if (!location.getText().toString().isEmpty()) {
            this.location = location.getText().toString();
        }*/

        if (!editSocialSit.getText().toString().isEmpty()) {
            this.socialSit = editSocialSit.getText().toString();
        }

        // Checks if the date is empty and if the format is correct
        if (!editDate.getText().toString().isEmpty()) {
            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            check.setLenient(false);

            try {
                this.date = check.parse(editDate.getText().toString());
            } catch(ParseException e) {
                e.printStackTrace();
                DATE_INVALID = true;
            }
        }

        if (SELECT_MOOD_INVALID) {
            TextView textview = (TextView) spinner.getSelectedView();
            textview.setError("");
            textview.setTextColor(Color.RED);
            textview.setText(R.string.invalid_mood);

            SELECT_MOOD_INVALID = false;

        } else if (DATE_INVALID) {
            editDate.setError("Invalid Date Inputted (yyyy-MM-DD)");
            DATE_INVALID = false;

        } else {
            if (!(this.context instanceof ProfileActivity)) {
                return makeMood();
            }
            else {
                return updateMood();
            }
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

    private Mood updateMood() {
        mood.setMoodType(this.moodType);
        mood.setMoodMsg(this.moodMsg);
        mood.setLocation(this.location);
        mood.setSocialSit(this.socialSit);
        mood.setDate(this.date);
        mood.setPhoto(mPhoto);

        return mood;
    }
}