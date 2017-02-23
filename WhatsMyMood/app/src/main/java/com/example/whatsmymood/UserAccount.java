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
    public MoodList moodList;
    public Follows follows;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        moodList = new MoodList();
        follows = new Follows();
    }

    public String getUsername() {
        return this.username;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String oldPassword, String newPassword) throws WrongPasswordException {
        if(this.password == oldPassword){
            this.password = newPassword;
        }else{
            throw new WrongPasswordException();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap newProfilePicture) {
        this.profilePicture = newProfilePicture;
    }
}
