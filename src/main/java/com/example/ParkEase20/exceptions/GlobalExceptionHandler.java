package com.example.ParkEase20.exceptions;

import com.example.ParkEase20.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyFoundException.class)
    public ResponseEntity<?> alreadyFoundException(AlreadyFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.CONTINUE.value(),
                LocalDateTime.now().toString()

        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> invalidCredential(InvalidCredentialsException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDto> fileStorageException(FileStorageException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now().toString()

        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(StatusException.class)
    public ResponseEntity<ErrorResponseDto> statusException(StatusException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponseDto> invalidInputException(InvalidInputException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
