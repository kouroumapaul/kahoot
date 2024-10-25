package com.example.kahoot.exception;

import org.springframework.http.HttpStatus;

public class InvalidAnswerException extends BaseException {
    public InvalidAnswerException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}