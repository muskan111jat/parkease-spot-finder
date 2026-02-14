package com.example.ParkEase20.exceptions;

public class NotFoundException extends RuntimeException{
    private final String message;
    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
