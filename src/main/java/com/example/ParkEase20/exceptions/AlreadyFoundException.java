package com.example.ParkEase20.exceptions;

public class AlreadyFoundException extends RuntimeException {
private final String message;
    public AlreadyFoundException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
