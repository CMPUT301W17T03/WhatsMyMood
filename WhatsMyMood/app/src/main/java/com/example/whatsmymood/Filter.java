package com.example.whatsmymood;

import android.util.Log;

import com.google.android.gms.wearable.Asset;

import junit.framework.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * Created by Alex on 4/2/2017.
 */

public class Filter {
    public final int RECENT = 1;
    public final int MOOD_TYPE = 2;
    public final int MOOD_MESSAGE = 3;

    private int type;
    private String value = null;

    public Filter(){this.type = 0;}

    public Filter(int type) {
        this.type = type;
    }

    public Filter(int type,String value){
        this.type = type;
        this.value = value;
    }

    public ArrayList<Mood> filterArray(ArrayList<Mood> moodList){

        ArrayList<Mood> filteredList = new ArrayList<Mood>();
        switch(this.type){

            case RECENT:

                // http://stackoverflow.com/questions/16982056/how-to-get-the-date-7-days-earlier-date-from-current-date-in-java
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -7);
                Date weekPast = cal.getTime();

                for (int i = 1; i < moodList.size(); i++) {
                    if (moodList.get(i).getDate().before(weekPast)) {
                        filteredList.add(moodList.get(i));
                    }
                }
                break;
            // Should make sure value != null ... not sure how to do that yet
            case MOOD_TYPE:
                try{
                    Assert.assertNotNull(value);
                    for (int i = 1; i < moodList.size(); i++) {
                        if (moodList.get(i).getMoodType().compareTo(value)==0) {
                            filteredList.add(moodList.get(i));
                        }
                    }
                } catch (AssertionError e) {
                    Log.i("error", "Value is null");
                    throw new RuntimeException();
                }
                break;

            case MOOD_MESSAGE:
                try{
                    Assert.assertNull(value);
                    for (int i = 1; i < moodList.size(); i++) {
                        if (moodList.get(i).getMoodMsg().toLowerCase().contains(value.toLowerCase())) {
                            filteredList.add(moodList.get(i));
                        }
                    }
                }catch(AssertionError e) {
                    Log.i("error", "Value is null");
                    throw new RuntimeException();
                }
                break;
            default:
                filteredList.addAll(moodList);
        }
        return filteredList;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
