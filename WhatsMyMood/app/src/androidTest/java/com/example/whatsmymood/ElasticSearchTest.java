package com.example.whatsmymood;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by Alex on 3/11/2017.
 */

public class ElasticSearchTest extends ActivityInstrumentationTestCase2 {

    private static JestDroidClient client;

    public ElasticSearchTest(){
        super(MainActivity.class);
    }

    public void testElasticSearch(){
        ElasticSearchUserController.verifySettings();

        String username = "testingusername";
        String password = "testingpassword";

        UserAccount user = new UserAccount(username, password);
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        addUserTask.execute(user);

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(username);

        try {
            ArrayList<UserAccount> userList = getUserTask.get();
            for (UserAccount User : userList) {
                assertEquals(User.getUsername(), username);
                assertEquals(User.getPassword(), password);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Delete the object after testing
        String deleteQuery = String.format("{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : " +
                "               { \"username\" : \"" + "%s" + "\" }\n" +
                "    }\n" +
                "}", username);

        DeleteByQuery delete = new DeleteByQuery.Builder(deleteQuery).addIndex("cmput301w17t03").addType("user").build();
        try {
            client.execute(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

