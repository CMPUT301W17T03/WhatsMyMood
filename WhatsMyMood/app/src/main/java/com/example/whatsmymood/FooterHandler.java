package com.example.whatsmymood;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/**
 * Created by Malcolm on 2017-03-08.
 */

public class FooterHandler {
    private View v;
    private Context mContext;
    private Dialog dialog;
    private boolean dialogActive;

    public FooterHandler(){};

    public FooterHandler(Context mContext, View v){
        this.v = v;
        this.mContext = mContext;
        build();
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    public void setView(View v){
        this.v = v;
    }

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
                Log.d("intent", "intent main");
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });

        this.v.findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO need a map activity
                Log.d("intent", "intent map");
            }
        });

        this.v.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("intent", "intent dialog");
                if(!dialogActive) {
                    dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.add_mood_popup);
                    dialog.show();
                    dialogActive = true;
                }else{
                    dialog.dismiss();
                    dialogActive = false;
                }
            }
        });





    }

}
