package com.user.User.Exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int id){
        super("user not found with user id: " + id);
    }
}
