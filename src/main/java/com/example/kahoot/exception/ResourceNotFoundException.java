package com.example.kahoot.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s with %s : '%s' not found", resourceName, fieldName, fieldValue),
                HttpStatus.NOT_FOUND
        );
    }
}
