package com.example.ParkEase20.dto;

public class ErrorResponseDto {
    private String message;
    private int status;
    private String timestamp;

    public ErrorResponseDto(String message, int status, String timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
