package com.example.whatsmymood;

/**
 * Created by Malcolm on 2017-03-11.
 *
 * Implements Lazy Singleton to hold Current User
 */
public class CurrentUser {

    // Sets the current instance to null
    // for the first creation of the singleton
    private static CurrentUser instance = null ;
    private UserAccount currentUser ;
    private CurrentUser() {
    }

    /**
     * Checks if the user is instantiated and returns
     * the current instance if it is instantiated
     * @return
     */
    public static CurrentUser getInstance() {
        if(instance == null){
            instance = new CurrentUser();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public UserAccount getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user
     * @param currentUser
     */
    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }
}