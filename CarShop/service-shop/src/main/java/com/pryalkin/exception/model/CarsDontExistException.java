package com.pryalkin.exception.model;

public class CarsDontExistException extends Exception {

    public CarsDontExistException(String message) {
        super(message);
    }
}
