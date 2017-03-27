package com.example.whatsmymood;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by YiJi on 2017-02-21.
 */

public class FollowsTest extends ActivityInstrumentationTestCase2{

    public FollowsTest(){
        super(MainActivity.class);
    }

    public void testGetFollowingList() {
        Relations user = new Relations();
        String username1 = "Supreme Overlord";
        String username2 = "Dictator";
        String username3 = "Master";

        user.addToFollowing(username1);
        user.addToFollowing(username2);
        user.addToFollowing(username3);

        ArrayList<String> returnFollowingList = user.getFollowingList();
        assertEquals(username1, returnFollowingList.get(0));
        assertEquals(username2, returnFollowingList.get(1));
        assertEquals(username3, returnFollowingList.get(2));
    }

    public void testGetFollowedByList() {
        Relations user = new Relations();
        String username1 = "Super Minion";
        String username2 = "Minion";
        String username3 = "Reserve Minion";

        user.addToFollowedBy(username1);
        user.addToFollowedBy(username2);
        user.addToFollowedBy(username3);

        ArrayList<String> returnFollowedByList = user.getFollowedByList();
        assertEquals(username1, returnFollowedByList.get(0));
        assertEquals(username2, returnFollowedByList.get(1));
        assertEquals(username3, returnFollowedByList.get(2));
    }

    public void testGetFollowRequestsList() {
        Relations user = new Relations();
        String username1 = "Persistent Minion-To-Be";
        String username2 = "Annoying Minion-To-Be";
        String username3 = "Desperate Minion-To-Be";

        user.addToFollowRequests(username1);
        user.addToFollowRequests(username2);
        user.addToFollowRequests(username3);

        ArrayList<String> returnFollowRequestsList = user.getFollowRequestsList();
        assertEquals(username1, returnFollowRequestsList.get(0));
        assertEquals(username2, returnFollowRequestsList.get(1));
        assertEquals(username3, returnFollowRequestsList.get(2));
    }

    public void testAddToFollowing(){
        Relations user = new Relations();
        String username = "Master";

        user.addToFollowing(username);
        assertTrue(user.hasFollowing(username));
    }

    public void testAddToFollowedBy(){
        Relations user = new Relations();
        String username = "Minion";

        user.addToFollowedBy(username);
        assertTrue(user.isFollowedBy(username));
    }

    public void testAddToFollowRequests(){
        Relations user = new Relations();
        String username = "Minion-To-Be";

        user.addToFollowRequests(username);
        assertTrue(user.hasRequests(username));
    }

    public void testRemoveFromFollowing(){
        Relations user = new Relations();
        String username = "Unreasonable Tyrant";

        user.addToFollowing(username);
        user.removeFromFollowing(username);

        assertFalse(user.hasFollowing(username));
    }

    public void testRemoveFromFollowedBy(){
        Relations user = new Relations();
        String username = "Useless Minion";

        user.addToFollowedBy(username);
        user.removeFromFollowedBy(username);

        assertFalse(user.isFollowedBy(username));
    }

    public void testRemoveFromFollowRequests(){
        Relations user = new Relations();
        String username = "Rejected Minion";

        user.addToFollowRequests(username);
        user.removeFromFollowRequests(username);

        assertFalse(user.hasRequests(username));
    }



}
