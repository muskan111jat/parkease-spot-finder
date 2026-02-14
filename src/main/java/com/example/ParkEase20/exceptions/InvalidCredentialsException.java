package com.example.ParkEase20.exceptions;

public class InvalidCredentialsException extends RuntimeException {
private final String message;
    public InvalidCredentialsException(String message) {
        super(message);
        this.message=message;
    }
}
