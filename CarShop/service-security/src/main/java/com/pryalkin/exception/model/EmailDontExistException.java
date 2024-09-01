package com.pryalkin.exception.model;

public class EmailDontExistException extends Exception{

    public EmailDontExistException(String message) {
        super(message);
    }
}
