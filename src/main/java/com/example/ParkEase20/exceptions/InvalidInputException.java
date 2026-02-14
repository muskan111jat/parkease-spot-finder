package com.example.ParkEase20.exceptions;

public class InvalidInputException extends RuntimeException{
    private String message;

    public InvalidInputException(String message) {
        super(message);
        this.message = message;
    }
}
