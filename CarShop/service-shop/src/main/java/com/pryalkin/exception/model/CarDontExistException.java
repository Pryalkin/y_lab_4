package com.pryalkin.exception.model;

public class CarDontExistException extends Exception{

    public CarDontExistException(String message) {
        super(message);
    }
}
