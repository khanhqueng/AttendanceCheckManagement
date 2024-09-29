package com.security.Jwt_service.exception;

import org.springframework.http.HttpStatus;

public class AppApiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public AppApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public AppApiException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
