package com.pryalkin.exceptions.model;

public class UsernameExistException extends Exception{
    public UsernameExistException(String message) {
        super(message);
    }
}
