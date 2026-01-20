package com.rest_api.spring_boot_rest_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNonNullException extends RuntimeException {
    public RequiredObjectIsNonNullException(String message) {
        super("Its is now allowed persist null object.");
    }

    public RequiredObjectIsNonNullException() {
        super("Its is now allowed persist null object.");
    }
}
