package com.example.whatsmymood;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;

import java.util.Date;

/**
 * Created by nathan on 21/02/17.
 */

public class Mood {
    private String moodType;
    private String moodAuthor;
    private String moodMsg;
    private Location location;
    private String socialSit;
    private Date date = new Date();
    private Bitmap Photo;

    public Mood(String moodAuthor, String moodType) {
        this.moodAuthor = moodAuthor;
        this.moodType = moodType;
    }

    // Manual Date Constructor
    public Mood(String moodAuthor, String moodType, Date date) {
        this.moodAuthor = moodAuthor;
        this.moodType = moodType;
        this.date = date;
    }

    public String getMoodType() {
        return moodType;
    }

    public void setMoodType(String newMoodType) {
        this.moodType = newMoodType;
    }

    public String getMoodAuthor() {
        return moodAuthor;
    }

    public void setMoodAuthor(String newmoodAuthor) {
        this.moodAuthor = newmoodAuthor;
    }

    public String getMoodMsg() {
        return moodMsg;
    }

    public void setMoodMsg(String newmoodMsg) {
        this.moodMsg = newmoodMsg;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLocation) {
        this.location = newLocation;
    }

    public String getSocialSit() {
        return socialSit;
    }

    public void setSocialSit(String newsocialSit) {
        this.socialSit = newsocialSit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date newdate) {
        this.date = newdate;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap newPhoto) {
        this.Photo = newPhoto;
    }
}