package com.example.solarservice.exception;

public class SolarPanelNotFoundException extends RuntimeException {

    public SolarPanelNotFoundException(String message) {
        super(message);
    }
}