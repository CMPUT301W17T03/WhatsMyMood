package com.example.whatsmymood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

/**
 * Created by YiJi on 2017-03-28.
 *
 * Reference used on March 28th, 2017 at 5:36pm
 * http://mrbool.com/how-to-change-the-layout-theme-of-an-android-application/25837
 *
 */


public class ThemeController {

    private final static String HAPPY_THEME = "Happiness";
    private final static String SAD_THEME = "Sadness";
    private final static String ANGRY_THEME = "Anger";
    private final static String CONFUSED_THEME = "Confusion";
    private final static String DISGUST_THEME = "Disgusted";
    private final static String SCARED_THEME = "Scared";
    private final static String SHAME_THEME = "Shame";
    private final static String SURPRISE_THEME = "Surprise";

    public static void onActivityStartSetTheme(Activity activity, String moodType){
        switch(moodType){
            case HAPPY_THEME:
                activity.setTheme(R.style.HappyTheme);
                break;
            case SAD_THEME:
                activity.setTheme(R.style.SadTheme);
                break;
            case ANGRY_THEME:
                activity.setTheme(R.style.AngerTheme);
                break;
            case CONFUSED_THEME:
                activity.setTheme(R.style.ConfusedTheme);
                break;
            case DISGUST_THEME:
                activity.setTheme(R.style.DisgustedTheme);
                break;
            case SCARED_THEME:
                activity.setTheme(R.style.ScaredTheme);
                break;
            case SHAME_THEME:
                activity.setTheme(R.style.ShameTheme);
                break;
            case SURPRISE_THEME:
                activity.setTheme(R.style.SurprisedTheme);
                break;
            default:
                activity.setTheme(R.style.DefaultTheme);
                break;
        }
    }
}