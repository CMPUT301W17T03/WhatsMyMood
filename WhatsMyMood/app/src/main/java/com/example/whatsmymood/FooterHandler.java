package com.example.whatsmymood;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malcolm on 2017-03-08.
 *@author mtfische
 *
 */

class FooterHandler {
    /**
     * FooterHandler handles the footer view that is displayed in activities
     * Creates on click listeners and handles page transitions
     *
     * @param mView            The footer View to be passed in and handled
     * @param mContext     The context of the main activity which calls the handler
     * @param dialog       dialog is the dialog the handler displays on certain button presses
     */
    private View mView;
    private Context mContext;
    private Dialog dialog;
    private Toast toast;
    private AddMoodController moodController;
    /**
     * Constructor to attach a view and context as well as initialize boolean values
     * build is called at the end to initialize the onclick listeners
     *
     * @param mContext The context of the main activity which calls the handler
     * @param view        The footer View to be passed in and handled
     */
    public FooterHandler(Context mContext, View view) {
        this.mView = view;
        this.mContext = mContext;
        this.toast = Toast.makeText(mContext, null, Toast.LENGTH_SHORT);
        build();
    }

    /**
     * Setter to set the context
     *
     * @param mContext The context of the main activity which calls the handler
     */
    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    /**
     * Setter to set the view to be handled build is called after a view shift
     *
     * @param v The footer View to be passed in and handled
     */
    public void setView(View v) {
        this.mView = v;
        build();
    }

    /**
     * Builder function which initializes the onClickListeners for the footer buttons
     */
    private void build() {
        this.mView.findViewById(R.id.follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FollowHubActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.mView.findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof ProfileActivity) {
                    toast.setText("Currently In Profile");
                    toast.show();
                } else {
                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        // http://stackoverflow.com/questions/5934050/check-whether-activity-is-active March 13, 2017 15:17
        this.mView.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof MainActivity) {
                    toast.setText("Currently In Home");
                    toast.show();
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        this.mView.findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mContext, MapActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.mView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.add_mood_popup);
                if(!dialog.isShowing()) {
                    moodController = new AddMoodController(mContext,dialog);
                    dialog.show();
                }else{
                    dialog.dismiss();
                }
            }
        });
    }

}
