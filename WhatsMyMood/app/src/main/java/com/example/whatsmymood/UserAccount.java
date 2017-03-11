package com.example.whatsmymood;

import android.graphics.Bitmap;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by Alex on 2/21/2017.
 *
 *@author czeto
 *@author mtfische
 *
 */
public class UserAccount {
    /**
     * UserAccount is used to store all of the users information
     *
     * @param username       The username of a user, used to identify them within the app
     * @param password       The user's password stored as a hash
     * @param name           The user's name used as an identity between users
     * @param profilePicture An image added by the user to describe themselves
     * @param moodlist       A class which contains methods to store moods
     * @see   MoodList
     * @param follows        A class which contains methods for following users
     * @see   Follows
     */
    private String username;
    private String password;
    private String name;
    private Bitmap profilePicture;
    public MoodList moodList;
    public Follows follows;

    @JestId
    private String id;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        moodList = new MoodList();
        follows = new Follows();
    }

    /**
     * Method to get the user's password
     *
     * @return the user's password hash
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Method to get the user's username
     *
     * @return the user's username
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *@param oldPassword              The password currently in use, stored as hash
     *@param newPassword              The password to change to, stored as hash
     *@throws WrongPasswordException  If the old password does not match the one in use
     */
    public void setPassword(String oldPassword, String newPassword) throws WrongPasswordException {
        if(this.password == oldPassword){
            this.password = newPassword;
        }else{
            throw new WrongPasswordException();
        }
    }

    /**
     * Method to get the user's name
     *
     * @return name of user as String
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the user's name
     *
     * @param newName    The name to set the user's name to
     */
    public void setName(String newName) {
        this.name = newName;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap newProfilePicture) {
        this.profilePicture = newProfilePicture;
    }

    public void setId(String id) {
        this.id = id;
    }

}
