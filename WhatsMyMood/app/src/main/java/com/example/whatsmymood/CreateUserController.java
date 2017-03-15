package com.example.whatsmymood;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Creates the User
 */
public class CreateUserController {

    // Blank Constructor for instantiating
    public CreateUserController(){
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean create(String username, String password) {
        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(username);

        try {
            ArrayList<UserAccount> userList = getUserTask.get();
            if (!userList.isEmpty()) {
                return false;
            } else {
                // Adds a user on success
                UserAccount user = new UserAccount(username, password);
                ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
                addUserTask.execute(user);
                return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}



