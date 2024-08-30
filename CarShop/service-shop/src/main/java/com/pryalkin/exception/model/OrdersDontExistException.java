package com.pryalkin.exception.model;

public class OrdersDontExistException extends Exception{

    public OrdersDontExistException(String message) {
        super(message);
    }
}
