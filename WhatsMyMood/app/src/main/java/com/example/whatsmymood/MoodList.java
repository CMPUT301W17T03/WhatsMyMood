package com.example.whatsmymood;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Malcolm on 2017-02-23.
 */

//TODO add observable implementation
public class MoodList {
    private ArrayList<Mood> moods;

    public MoodList() {
        this.moods = new ArrayList<Mood>();
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
        //TODO search earliest date
        return null;
    }

    /**
     * Gets the sorted mood list
     * @return the array list sorted by date sescending
     */
    public ArrayList<Mood> getSortedMoodList(){
        //TODO sort the moods by date
        Collections.sort(this.moods);
        return this.moods;
    }

    /**
     * Gets the mood list
     * @return the moodlist
     */
    public ArrayList<Mood> getMoodList(){
        return this.moods;
    }
}
