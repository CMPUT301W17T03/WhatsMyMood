package com.example.whatsmymood;

/**
 * Created by Malcolm on 2017-02-23.
 */

public class WrongPasswordException extends Exception {

    private String message;

    public WrongPasswordException(){}

    public WrongPasswordException(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
