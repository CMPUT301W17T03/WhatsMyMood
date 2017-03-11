package com.example.whatsmymood;

/**
 * Created by Malcolm on 2017-03-11.
 *
 * Implements Lazy Singleton to hold Current User
 */
public class CurrentUser {
    private  UserAccount currentUser;
    private CurrentUser() {}

    private static class LazyHolder {
        private static final CurrentUser INSTANCE = new CurrentUser();
    }

    public static CurrentUser getInstance() {
        return LazyHolder.INSTANCE;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }
}