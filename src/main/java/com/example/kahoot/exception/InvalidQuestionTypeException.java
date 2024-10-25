package com.example.kahoot.exception;

import org.springframework.http.HttpStatus;

public class InvalidQuestionTypeException extends BaseException {
    public InvalidQuestionTypeException(String questionType) {
        super(
                String.format("Invalid question type: '%s'", questionType),
                HttpStatus.BAD_REQUEST
        );
    }
}
