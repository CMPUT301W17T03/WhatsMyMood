package com.example.whatsmymood;

import java.util.ArrayList;
import java.util.Collections;

/**
 * MoodList object holds an ArrayList of moods
 * to keep track of the moods of each user
 */
public class MoodList {
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
        if (moods.size() == 0) {
            return null;
        } else {
            Mood mostRecentMood = moods.get(0);
            for (int i = 1; i < this.getSize(); i++) {
                if (moods.get(i).getDate().after(mostRecentMood.getDate())) {
                    mostRecentMood = moods.get(i);
                }
            }
            return mostRecentMood;
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
}
