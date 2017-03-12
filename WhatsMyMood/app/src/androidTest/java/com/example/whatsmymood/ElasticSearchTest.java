package com.example.whatsmymood;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alex on 3/11/2017.
 */

public class ElasticSearchTest extends ActivityInstrumentationTestCase2 {

    public ElasticSearchTest(){
        super(MainActivity.class);
    }

    //Unsure if test is weird, or if ElasticSearchUserController is weird
    public void testElasticSearch(){
        UserAccount user = new UserAccount("Username", "Password");
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        addUserTask.execute(user);

        String search = "Username";
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        //getUserTask.execute(search);

        try{
            getUserTask.execute(search);
            ArrayList<UserAccount> getUser = getUserTask.get();

            Log.d("yiji",getUser.toString());
            Log.d("yiji",getUser.get(0).toString());
            Log.d("yiji",user.toString());
            //assertTrue(getUser.contains(user));
            assertEquals(getUser.get(0), user);
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

    }
}

