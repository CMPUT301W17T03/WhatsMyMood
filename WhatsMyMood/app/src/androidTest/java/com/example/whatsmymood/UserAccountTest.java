package com.example.whatsmymood;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Malcolm on 2017-02-23.
 */

public class UserAccountTest extends ActivityInstrumentationTestCase2 {

    public UserAccountTest() {
        super(MainActivity.class);
    }


    public void testGetUsername() {
        String username = "superCoolUsername";
        String password = "superSecretPassword";
        UserAccount user = new UserAccount(username, password);

        assertEquals(username, user.getUsername());
    }

    public void testGetPassword() {
        String username = "superCoolUsername";
        String password = "superSecretPassword";
        UserAccount user = new UserAccount(username, password);

        assertEquals(password, user.getPassword());
    }

    public void testGetName() {
        String username = "superCoolUsername";
        String password = "superSecretPassword";
        String name = "Arbitrary Name";
        UserAccount user = new UserAccount(username, password);
        user.setName(name);

        assertEquals(name, user.getName());
    }

    public void testSetPassword() {
        String username = "superCoolUsername";
        String password = "superSecretPassword";
        UserAccount user = new UserAccount(username, password);

        try {
            user.setPassword(password, "newSuperSecretPassword");
            assertEquals(user.getPassword(),"newSuperSecretPassword");
            user.setPassword("Banana", "apple");
            fail();
        } catch (WrongPasswordException e) {}
    }

    /*
    public Bitmap getProfilePicture() {
        return profilePicture;
    }


    public ArrayList<Mood> getMoodList() {
        return moodList;
    }
   */


}