package com.security.Jwt_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class ResourceDuplicateException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    public ResourceDuplicateException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s has duplicate result with %s: %s", resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
