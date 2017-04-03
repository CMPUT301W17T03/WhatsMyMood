package com.example.whatsmymood;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.wearable.Asset;

import junit.framework.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * Controller class that filters arrays of moods based on the type of filter selected.<b>
 * Filter is also passed from profile or main activity to the map to display the filter
 * mood list on a map
 * @author Alex
 * @version 1.2 , 4/2/2017
 */

public class Filter implements Parcelable {
    public final static int NONE = 0;
    public final static int RECENT = 1;
    public final static int MOOD_TYPE = 2;
    public final static int MOOD_MESSAGE = 3;
    public final static int FIVE_KM = 4;

    private int type;
    private Bundle bundle;
    private String value;

    public Filter(){this.type = NONE;}

    public Filter(int type) {
        this.type = type;
    }

    public Filter(int type,String value){
        this.type = type;
        this.value = value;
    }

    /**
     * Filter mood array list.
     *
     * @param moodList the mood list
     * @return filtered mood array list
     */
    public ArrayList<Mood> filterArray(ArrayList<Mood> moodList){

        ArrayList<Mood> filteredList = new ArrayList<Mood>();
        switch(this.type){
            case NONE:
                filteredList.addAll(moodList);
                break;
            case RECENT:

                // http://stackoverflow.com/questions/16982056/how-to-get-the-date-7-days-earlier-date-from-current-date-in-java

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -7);
                Date weekPast = cal.getTime();


                for (int i = 0; i < moodList.size(); i++) {
                    if (moodList.get(i).getDate().after(weekPast)) {
                        filteredList.add(moodList.get(i));
                    }

                }
                break;
            // Should make sure value != null ... not sure how to do that yet
            case MOOD_TYPE:
                try{
                    Assert.assertNotNull(value);
                    for (int i = 0; i < moodList.size(); i++) {
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
                    Log.d("filter","filter value:"+this.value);
                    for (int i = 0; i < moodList.size(); i++) {
                        if(moodList.get(i).getMoodMsg() != null) {
                            if (moodList.get(i).getMoodMsg().toLowerCase().contains(value.toLowerCase())) {
                                filteredList.add(moodList.get(i));
                            }
                        }
                    }
                }catch(NullPointerException e) {
                    Log.d("filter","null pointer exception");
                    return moodList;
                }
                break;
            case FIVE_KM:
                Location LastKnownLocation = bundle.getParcelable("location");
                Log.d("FIVE_KM",LastKnownLocation.toString());
                for (int i = 0; i < moodList.size(); i++) {
                    Mood mood = moodList.get(i);
                    if (mood.getLocation() != null) {
                        Location moodLocation = new Location("");
                        moodLocation.setLatitude(mood.getLocation().latitude);
                        moodLocation.setLongitude(mood.getLocation().longitude);
                        if (moodLocation.distanceTo(LastKnownLocation) <= 5000) {
                            filteredList.add(moodList.get(i));
                        }
                    }
                }
                break;
            default:
                filteredList.addAll(moodList);
        }
        return filteredList;
    }

    /**
     * Sets the string variable value.
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }
    public String getValue() {
        return value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBundle(Bundle bundle) {this.bundle = bundle;}

    protected Filter(Parcel in) {
        //RECENT = in.readInt();
        //MOOD_TYPE = in.readInt();
        //MOOD_MESSAGE = in.readInt();
        type = in.readInt();
        value = in.readString();
        bundle = in.readBundle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(RECENT);
        //dest.writeInt(MOOD_TYPE);
        //dest.writeInt(MOOD_MESSAGE);
        dest.writeInt(type);
        dest.writeString(value);
        dest.writeBundle(bundle);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
}