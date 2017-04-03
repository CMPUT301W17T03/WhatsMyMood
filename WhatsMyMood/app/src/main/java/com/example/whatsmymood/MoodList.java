package com.example.whatsmymood;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * MoodList object holds an ArrayList of moods
 * to keep track of the moods of each user
 * @author Malcom
 */
public class MoodList implements Parcelable {
    private final ArrayList<Mood> moods;

    public MoodList() {
        this.moods = new ArrayList<>();
    }

    /**
     * appends a mood to the list
     * @param mood
     */
    public void addMood(Mood mood){
        this.moods.add(mood);
    }

    /**
     * inserts a mood to the list at index
     * @param index
     * @param mood
     */
    public void addMood(int index, Mood mood){
        this.moods.add(index, mood);
    }

    /**
     * removes a mood from the list matching mood
     * @param mood
     */
    public void removeMood(Mood mood){
        this.moods.remove(mood);
    }
    /**
     * removes a mood from the list at index
     * @param index
     */
    public void removeMood(int index){
        this.moods.remove(index);
    }

    /**
     * Get's index of the mood
     * @param mood
     * @return The index position of mood
     */
    public int getIndex(Mood mood){
        for (int i = 0; i < this.getSize(); i++){
            if(moods.get(i) == mood){return i;}
        }
        return -1;
    }

    /**
     * Get's mood from index
     * @param index
     * @return the mood at index position
     */
    public Mood get(int index){
        return this.moods.get(index);
    }

    /**
     * Gets the size of the moodList
     * @return an integer of the size
     */
    public int getSize(){
        return this.moods.size();
    }

    /**
     * Get's the most recent mood
     * @return the mood with the earliest date
     */
    public Mood getRecentMood(){
        try {
            Mood mostRecentMood = moods.get(0);
            for (int i = 1; i < this.getSize(); i++) {
                if (moods.get(i).getDate().after(mostRecentMood.getDate())) {
                    mostRecentMood = moods.get(i);
                }
            }
            return mostRecentMood;
        }catch(IndexOutOfBoundsException e){
            return null;
        }

    }

    /**
     * Gets the sorted mood list
     * @return the array list sorted by date ascending
     */
    public ArrayList<Mood> getSortedMoodList(){
        Collections.sort(this.moods);
        return this.moods;
    }

    /**
     * Gets the mood list
     * @return the mood list
     */
    public ArrayList<Mood> getMoodList(){
        return this.moods;
    }

    protected MoodList(Parcel in) {
        if (in.readByte() == 0x01) {
            moods = new ArrayList<Mood>();
            in.readList(moods, Mood.class.getClassLoader());
        } else {
            moods = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (moods == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(moods);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MoodList> CREATOR = new Parcelable.Creator<MoodList>() {
        @Override
        public MoodList createFromParcel(Parcel in) {
            return new MoodList(in);
        }

        @Override
        public MoodList[] newArray(int size) {
            return new MoodList[size];
        }
    };
}