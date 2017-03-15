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

public class FooterHandler {
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
    private AddMoodController moodController;
    
    /**
     * Constructor to attach a view and context as well as initialize boolean values
     * build is called at the end to initialize the onclick listeners
     *
     * @param mContext The context of the main activity which calls the handler
     * @param view        The footer View to be passed in and handled
     * @see build
     */
    public FooterHandler(Context mContext, View view){
        this.mView = view;
        this.mContext = mContext;
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.add_mood_popup);
        moodController = new AddMoodController(mContext,dialog,view);
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
     * @see build
     */
    public void setView(View v){
        this.mView = v;
        build();
    }

    /**
     * Builder function which initializes the onClickListeners for the footer buttons
     */
    private void build(){
        //TODO implementation
        this.mView.findViewById(R.id.follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO need a follow hub activity
                Log.d("intent", "intent follow");
                Intent intent = new Intent(mContext, FollowHubActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.mView.findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO need a profile activity
                Log.d("intent", "intent profile");
            }
        });


        // http://stackoverflow.com/questions/5934050/check-whether-activity-is-active March 13, 2017 15:17
        this.mView.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> runningActivities = new ArrayList<>();

                ActivityManager activityManager = (ActivityManager) mContext.getSystemService (Context.ACTIVITY_SERVICE);

                List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);

                for (int i1 = 0; i1 < services.size(); i1++) {
                    runningActivities.add(0,services.get(i1).topActivity.toString());
                }

                if(runningActivities.contains("ComponentInfo{com.example.whatsmymood/com.example.whatsmymood.MainActivity}")){
                    Toast.makeText(mContext, "Currently In Home", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });

        this.mView.findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("intent", "intent map");
                Intent intent = new Intent (mContext, MapsActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.mView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("intent", "intent dialog");
                if(!dialog.isShowing()) {
                    dialog.show();
                }else{
                    dialog.dismiss();
                }
            }
        });
    }

}
