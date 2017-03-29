package com.example.whatsmymood;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Mood object that holds mood details
 */
public class Mood implements Comparable<Mood>{
    private String moodType;
    private String moodAuthor;
    private String moodMsg;

    // TODO: Use an actual location instead of a string
    //private Location location;
    private String location;
    private String socialSit;
    private Date date;
    private String photo;

    /**
     * @param moodAuthor
     * @param moodType
     */
    public Mood(String moodAuthor, String moodType) {
        this.moodAuthor = moodAuthor;
        this.moodType = moodType;
        this.date = new Date();
    }

    /**
     * Manual Date
     * @param moodAuthor
     * @param moodType
     * @param date
     */
    public Mood(String moodAuthor, String moodType, Date date) {
        this.moodAuthor = moodAuthor;
        this.moodType = moodType;
        this.date = date;
    }

    /**
     * Getters and Setters
     * @return
     */
    public String getMoodType() {
        return moodType;
    }

    public void setMoodType(String newMoodType) {
        this.moodType = newMoodType;
    }

    public String getMoodAuthor() {
        return moodAuthor;
    }

    public void setMoodAuthor(String newMoodAuthor) {
        this.moodAuthor = newMoodAuthor;
    }

    public String getMoodMsg() {
        return moodMsg;
    }

    public void setMoodMsg(String newmoodMsg) {
        this.moodMsg = newmoodMsg;
    }

    // TODO: Use an actual location instead of a string
    public String getLocation() { return location; }

    public void setLocation(String newLocation) {this.location = newLocation; }


    public String getSocialSit() {
        return socialSit;
    }

    public void setSocialSit(String newSocialSit) {
        this.socialSit = newSocialSit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String newPhoto) {
        this.photo = newPhoto;
    }

    //http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
    //February 26th, 2016 - 1:02pm
    public int compareTo(@NonNull Mood o) {
        return getDate().compareTo(o.getDate());
    }
}
