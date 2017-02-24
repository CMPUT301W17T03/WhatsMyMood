package com.example.whatsmymood;

import java.util.ArrayList;

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
    public void addMood(int index, Mood mood){
        this.moods.add(index, mood);
    }
    public void removeMood(Mood mood){
        this.moods.remove(mood);
    }
    public void removeMood(int index){
        this.moods.remove(index);
    }

    public int getIndex(Mood mood){
        for (int i = 0; i < this.getSize(); i++){
            if(moods.get(i) == mood){return i;}
        }
        return -1;
        //TODO search for mood
    }
    public Mood get(int index){
        return this.moods.get(index);
    }

    public int getSize(){
        return this.moods.size();
    }

    public Mood getRecentMood(){
        //TODO search earliest date
        return null;
    }

    public ArrayList<Mood> getSortedMoodList(){
        //TODO sort the moods by date
        return this.moods;
    }

    public ArrayList<Mood> getMoodList(){
        return this.moods;
    }
}
