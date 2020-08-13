package com.example.springtest.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Integer id) {
        super("Could not find entity " + id);
    }
}
