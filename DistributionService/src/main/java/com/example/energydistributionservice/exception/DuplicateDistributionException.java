package com.example.energydistributionservice.exception;

public class DuplicateDistributionException extends RuntimeException {

    public DuplicateDistributionException(String message) {
        super(message);
    }
}