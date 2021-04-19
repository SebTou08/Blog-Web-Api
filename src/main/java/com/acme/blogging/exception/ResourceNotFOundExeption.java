package com.acme.blogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFOundExeption extends RuntimeException {
    public ResourceNotFOundExeption() {
        super();
    }

    public ResourceNotFOundExeption(String message) {
        super(message);
    }

    public ResourceNotFOundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFOundExeption(String resourceName, String fieldName, Object fieldValue){
        super(String.format("Resource $s not found for $s with values $s", resourceName, fieldName, fieldValue));
    }
}


