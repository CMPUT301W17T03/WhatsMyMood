package com.example.whatsmymood;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Malcolm on 2017-03-11.
 */

public class CreateUserController {

    public CreateUserController(){
    }

    public boolean create(String username, String password) {

        // Check that user name is unique in elastic search index

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(username);

        try {
            ArrayList<UserAccount> userList = getUserTask.get();
            if (!userList.isEmpty()) {
                return false;
            } else {
                UserAccount user = new UserAccount(username, password);
                ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
                addUserTask.execute(user);
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    }


}
