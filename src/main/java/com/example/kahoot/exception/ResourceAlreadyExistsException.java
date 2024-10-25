package com.example.kahoot.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseException {
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s with %s : '%s' already exists", resourceName, fieldName, fieldValue),
                HttpStatus.CONFLICT
        );
    }
}
