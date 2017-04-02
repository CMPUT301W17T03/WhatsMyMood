package com.example.whatsmymood;

/**
 * Created by Alex on 4/2/2017.
 */

public class Filter {

    private static final Filter ourInstance = new Filter();

    public static Filter getInstance() {
        return ourInstance;
    }

    private Filter() {
    }
}
