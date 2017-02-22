package com.example.whatsmymood;

import java.util.ArrayList;

/**
 * Created by YiJi on 2017-02-21.
 */
public class Follows {

    private ArrayList<String> followingList;
    private ArrayList<String> followedByList;
    private ArrayList<String> followRequestsList;

    public Follows(){
        this.followingList = followingList;
        this.followedByList = followedByList;
        this.followRequestsList = followRequestsList;
    }

    public ArrayList<String> getFollowingList() {
        return followingList;
    }

    public ArrayList<String> getFollowedByList() {
        return followedByList;
    }

    public ArrayList<String> getFollowRequestsList() {
        return followRequestsList;
    }

    public void addToFollowing(String username){
        followingList.add(username);
    }

    public void addToFollowedBy(String username){
        followedByList.add(username);
    }

    public void addToFollowRequests(String username){
        followRequestsList.add(username);
    }

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
