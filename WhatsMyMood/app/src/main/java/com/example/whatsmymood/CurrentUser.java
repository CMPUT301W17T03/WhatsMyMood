package com.example.whatsmymood;

/**
 * Created by Malcolm on 2017-03-11.
 *
 * Implements Lazy Singleton to hold Current User
 */
public class CurrentUser {

    private static CurrentUser instance = null ;
    private UserAccount currentUser ;
    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if(instance == null){
            instance = new CurrentUser();
        }
        return instance;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }
}