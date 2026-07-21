package com.example.batteryservice.exception;

public class DuplicateBatteryException extends RuntimeException {

    public DuplicateBatteryException(String message) {
        super(message);
    }
}