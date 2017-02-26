package com.example.whatsmymood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Malcolm on 2017-02-23.
 */


public class MoodListTest extends ActivityInstrumentationTestCase2 {

    public MoodListTest() {
        super(MainActivity.class);
    }

    public void testAddMood() {
        Mood mood = new Mood("Nathan", "Happy");
        Mood mood2 = new Mood("Nathan", "Sad");
        MoodList list = new MoodList();
        list.addMood(mood);
        assertEquals(list.get(0), mood);

        list.addMood(0,mood2);
        assertEquals(list.get(0), mood2);
    }

    public void testRemoveMood() {
        Mood mood = new Mood("Nathan", "Happy");
        Mood mood2 = new Mood("Nathan", "Sad");
        MoodList list = new MoodList();
        list.addMood(mood);
        list.removeMood(0);
        assertEquals(list.getSize(), 0);

        list.addMood(mood2);
        list.removeMood(mood2);
        assertEquals(list.getSize(), 0);
    }

    public void testGetRecentMood(){

        MoodList moods = new MoodList();
        Mood mood1 = new Mood("Person1", "Angry");
        Mood mood2 = new Mood("Person2", "Happy");
        Mood mood3 = new Mood("Prson3", "Sad");

        assertEquals(moods.getRecentMood(), mood3);

    }

    public void testGetSortedMoodList(){

        MoodList moods = new MoodList();
        Mood mood1 = new Mood("Person1", "Sad");
        Mood mood2 = new Mood("Person2", "Angry");
        Mood mood3 = new Mood("Person3", "Happy");

        moods.addMood(mood1);
        moods.addMood(mood2);
        moods.addMood(mood3);

        ArrayList<Mood> sortedmoods = new ArrayList<Mood>();
        sortedmoods = moods.getSortedMoodList();

        for (int i = 1; i<sortedmoods.size(); i++){
            assertFalse(sortedmoods.get(i-1).getDate().before(sortedmoods.get(i).getDate()));
        }
    }


    public void testGetMoodList(){
        MoodList list = new MoodList();
        Mood mood1 = new Mood("person1", "happy");
        Mood mood2 = new Mood("person2", "sad");
        Mood mood3 = new Mood("person3", "confused");

        list.addMood(mood1);
        list.addMood(mood2);
        list.addMood(mood3);

        ArrayList<Mood> returnList = list.getMoodList();
        assertEquals(mood1, returnList.get(0));
        assertEquals(mood2, returnList.get(1));
        assertEquals(mood3, returnList.get(2));
    }
}

