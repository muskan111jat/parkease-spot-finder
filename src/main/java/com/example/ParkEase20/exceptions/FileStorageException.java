package com.example.ParkEase20.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageException extends RuntimeException{
    private String message;
    private Throwable cause;
}
