package com.example.windservice.exception;

public class DuplicateWindTurbineException extends RuntimeException {

    public DuplicateWindTurbineException(String message) {
        super(message);
    }
}