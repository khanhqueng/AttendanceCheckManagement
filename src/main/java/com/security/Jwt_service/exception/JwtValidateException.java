package com.security.Jwt_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class JwtValidateException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public JwtValidateException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
