package com.example.whatsmymood;

/**
 * Created by Malcolm on 2017-03-11.
 */

public class CreateUserController {

    public CreateUserController(){
    }

    public void create(String username, String password){

        CurrentUser current = CurrentUser.getInstance();

        UserAccount user = new UserAccount(username,password);

        current.setCurrentUser(user);

        // TODO add to Elastic Search
        // TODO check that username is unique using Elastic Search
    }
}
