package com.pryalkin.exception.model;

public class EntityDontExistException extends Exception{

    public EntityDontExistException() {
        super("Сущность не существует!");
    }
}
