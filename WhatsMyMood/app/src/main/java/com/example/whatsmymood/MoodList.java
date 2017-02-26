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

    public void addMood(Mood mood){
        this.moods.add(mood);
    }

    /**
     * Adds and Removes moods from the list
     * @param index
     * @param mood
     */
    public void addMood(int index, Mood mood){
        this.moods.add(index, mood);
    }
    public void removeMood(Mood mood){
        this.moods.remove(mood);
    }
    public void removeMood(int index){
        this.moods.remove(index);
    }

    /**
     * Get's index of the mood
     * @param mood
     * @return
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
     * @return
     */
    public Mood get(int index){
        return this.moods.get(index);
    }

    /**
     * Gets the size of the moodList
     * @return
     */
    public int getSize(){
        return this.moods.size();
    }

    /**
     * Get's the most recent mood
     * @return
     */
    public Mood getRecentMood(){
        Mood mostRecentMood = moods.get(0);
        for (int i = 1; i < this.getSize(); i++) {
            if (moods.get(i).getDate().before(mostRecentMood.getDate())) {
                mostRecentMood = moods.get(i);
            }
        }
        return mostRecentMood;
    }

    /**
     * Gets the sorted mood list
     * @return
     */
    public ArrayList<Mood> getSortedMoodList(){
        Collections.sort(this.moods);
        return this.moods;
    }

    /**
     * Gets the mood list
     * @return
     */
    public ArrayList<Mood> getMoodList(){
        return this.moods;
    }
}
