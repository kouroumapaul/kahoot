package com.example.kahoot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// Base exception for all custom exceptions
@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
