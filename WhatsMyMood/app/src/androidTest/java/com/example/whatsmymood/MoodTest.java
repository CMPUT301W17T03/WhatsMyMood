package com.example.whatsmymood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nathan on 21/02/17.
 */

public class MoodTest extends ActivityInstrumentationTestCase2 {

    public MoodTest() {
        super(MainActivity.class);
    }

    public void testGetMoodType() {
        Mood mood = new Mood("Nathan", "Happy");

        assertEquals(mood.getMoodType(), "Happy");

        Mood mood2 = new Mood("Nathan", "Happy");
        mood2.setMoodType("Sad");

        assertEquals(mood2.getMoodType(), "Sad");
    }

    public void testGetMoodAuthor() {
        Mood mood1 = new Mood("Nathan", "Happy");

        assertEquals(mood1.getMoodAuthor(), "Nathan");

        Mood mood2 = new Mood("Nathan", "Happy");
        mood2.setMoodAuthor("Eddy");

        assertEquals(mood2.getMoodAuthor(), "Eddy");

    }

    public void testGetMoodMessage() {
        Mood mood = new Mood("Nathan", "Happy");
        mood.setMoodMsg("Wow!");

        assertEquals(mood.getMoodMsg(), "Wow!");
    }

    public void testGetLocation() {
        Mood mood = new Mood("Nathan", "Happy");
        Location location = new Location("provider");
        location.setLatitude(100.0);
        location.setLongitude(50.0);
        mood.setLocation(location);

        assertEquals(mood.getLocation().getLatitude(), 100.0);
        assertEquals(mood.getLocation().getLongitude(), 50.0);
    }

    public void testGetDate() {
        Mood mood1 = new Mood("Nathan", "Happy");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-08");
            Mood mood2 = new Mood("Nathan", "Happy", date);
            mood1.setDate(date);

            assertEquals(mood1.getDate(), date);
            assertEquals(mood2.getDate(), date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void testGetSocialSit() {
        Mood mood = new Mood("Nathan", "Happy");
        mood.setSocialSit("With Eddy");

        assertEquals(mood.getSocialSit(), "With Eddy");
    }

    public void testGetPhoto() {
        Mood mood = new Mood("Nathan", "Happy");

        Bitmap bitmap = BitmapFactory.decodeResource(null, R.mipmap.ic_launcher);
        mood.setPhoto(bitmap);

        assertEquals(mood.getPhoto(), bitmap);
    }
}