package com.example.whatsmymood;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alex on 3/11/2017.
 */

public class ElasticSearchTest extends ActivityInstrumentationTestCase2 {

    private String username = "WOWOOWOW";
    private static final String TAG = "hello1";

    public ElasticSearchTest(){
        super(MainActivity.class);
    }

    //Unsure if test is weird, or if ElasticSearchUserController is weird
    public void testElasticSearch(){

        /* PLEASE DO NOT MAKE MULTIPLE USERS OR IT FLOODS ELASTICSEARCH
        UserAccount user = new UserAccount(username, "ITWORKS WOWOWOO");
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        addUserTask.execute(user);
        */


        String search = username;
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(search);
        try {
            ArrayList<UserAccount> userList = getUserTask.get();
            for (UserAccount User : userList) {
                Log.d(TAG, User.getUsername());
                Log.d(TAG, User.getPassword());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

