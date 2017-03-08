package com.example.whatsmymood;

import android.widget.EditText;

/**
 * Created by nathan on 07/03/17.
 */

public class AddMood {
    private EditText moodType;

    // Lazy Singleton
    private static AddMood instance = null;

    private AddMood() {}

    public static AddMood getInstance() {
        if (instance == null) {
            instance = new AddMood();
        }
        return instance;
    }

}