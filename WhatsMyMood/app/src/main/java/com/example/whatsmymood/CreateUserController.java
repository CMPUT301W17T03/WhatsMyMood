package com.example.whatsmymood;

/**
 * Created by Malcolm on 2017-03-11.
 */

public class CreateUserController {

    public CreateUserController(){
    }

    public boolean create(String username, String password){

        UserAccount user = new UserAccount(username,password);

        return true;
        // TODO add to Elastic Search
        // TODO check that username is unique using Elastic Search
        // TODO return false if username already exists in Elastic Search
    }


}
