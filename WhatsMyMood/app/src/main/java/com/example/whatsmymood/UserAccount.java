package com.example.whatsmymood;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Alex on 2/21/2017.
 */

public class UserAccount {
    private String username;
    private String password;
    private String name;
    private Bitmap profilePicture;
    private ArrayList<Mood> moodList;
    public Follows follows;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String oldPassword, String newPassword) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = name;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap newProfilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<Mood> getMoodList() {
        return moodList;
    }
}
