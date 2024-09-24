package com.example.kahoot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiErrorResponse {
    private int status;
    private String message;
    private String description;
    private long timestamp;

    public ApiErrorResponse(int status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }
}
