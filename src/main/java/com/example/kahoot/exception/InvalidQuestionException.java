package com.example.kahoot.exception;

import org.springframework.http.HttpStatus;

public class InvalidQuestionException extends BaseException {
    public InvalidQuestionException(String questionType) {
        super(
                String.format("Invalid question type: '%s'", questionType),
                HttpStatus.BAD_REQUEST
        );
    }
}
