package com.example.solarservice.exception;

public class DuplicateSolarPanelException extends RuntimeException {

    public DuplicateSolarPanelException(String message) {
        super(message);
    }
}