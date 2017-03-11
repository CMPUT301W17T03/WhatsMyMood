package com.example.whatsmymood;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/**
 * Created by Malcolm on 2017-03-08.
 *@author mtfische
 *
 */

public class FooterHandler {
    /**
     * FooterHandler handles the footer view that is displayed in activities
     * Creates on click listners and handles page transitions
     *
     * @param v            The footer View to be passed in and handled
     * @param mContext     The context of the main activity which calls the handler
     * @param dialog       dialog is the dialog the handler displayes on certain button presses
     * @param dialogActive boolean to see if the dialog is showing :: DEPRECIATED
     */
    private View v;
    private Context mContext;
    private Dialog dialog;
    private boolean dialogActive;

    /**
     * Blank Constructor
     */
    public FooterHandler(){};

    /**
     * Constructor to attach a view and context as well as initialize boolean values
     * build is called at the end to initialize the onclick listners
     *
     * @param mContext The context of the main activity which calls the handler
     * @param v        The footer View to be passed in and handled
     * @see build
     */
    public FooterHandler(Context mContext, View v){
        this.v = v;
        this.mContext = mContext;
        dialogActive = false;
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
        this.v = v;
        build();
    }

    /**
     * Builder function which initializes the onClickListners for the footer buttons
     */
    private void build(){
        //TODO implementation
        this.v.findViewById(R.id.follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO need a follow hub activity
                Log.d("intent", "intent follow");
            }
        });

        this.v.findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO need a profile activity
                Log.d("intent", "intent profile");
            }
        });

        this.v.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle case where home activity is in the stack multiple times in a row
                Log.d("intent", "intent main");
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.v.findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("intent", "intent map");
                Intent intent = new Intent (mContext, MapsActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.v.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("intent", "intent dialog");
                dialog = new Dialog(mContext); // Moved this line because I was getting a null object reference - Nathan
                if(!dialog.isShowing()) {
                    //dialogActive = true;
                    dialog.setContentView(R.layout.add_mood_popup);
                    dialog.show();
                }else{
                    dialog.dismiss();
                    //dialogActive = false;
                }
            }
        });
    }

}
