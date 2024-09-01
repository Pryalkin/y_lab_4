package com.pryalkin.exception.model;

public class UserDontExistException extends Exception{
    public UserDontExistException(String message) {
        super(message);
    }
}
