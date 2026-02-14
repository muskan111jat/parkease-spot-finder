package com.example.ParkEase20.exceptions;

public class StatusException extends RuntimeException{
    private String message;
    public StatusException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
