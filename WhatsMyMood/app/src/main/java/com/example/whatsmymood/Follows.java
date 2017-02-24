package com.example.whatsmymood;

import java.util.ArrayList;

/**
 * Created by YiJi on 2017-02-21.
 */

public class Follows {
    private ArrayList<String> followingList = new ArrayList<String>();
    private ArrayList<String> followedByList = new ArrayList<String>();
    private ArrayList<String> followRequestsList = new ArrayList<String>();

    /**
     * Testing Methods
     * @param username
     * @return
     */
    public boolean hasFollowing(String username){
        return followingList.contains(username);
    }
    public boolean isFollowedBy(String username){
        return followedByList.contains(username);
    }
    public boolean hasRequests(String username){
        return followRequestsList.contains(username);
    }

    /**
     * Getters for follow lists
     * @return
     */
    public ArrayList<String> getFollowingList() {
        return followingList;
    }

    public ArrayList<String> getFollowedByList() {
        return followedByList;
    }

    public ArrayList<String> getFollowRequestsList() {
        return followRequestsList;
    }

    /**
     * Add's users to lists
     * @param username
     */
    public void addToFollowing(String username){
        followingList.add(username);
    }

    public void addToFollowedBy(String username){
        followedByList.add(username);
    }

    public void addToFollowRequests(String username){
        followRequestsList.add(username);
    }

    /**
     * Removes users from lists
     * @param username
     */
    public void removeFromFollowing(String username){
        followingList.remove(username);
    }

    public void removeFromFollowedBy(String username){
        followedByList.remove(username);
    }

    public void removeFromFollowRequests(String username){
        followRequestsList.remove(username);
    }

}
