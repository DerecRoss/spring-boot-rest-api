package com.rest_api.spring_boot_rest_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super("Unsupported file extension.");
    }

    public BadRequestException() {
        super("Its is now allowed persist null object.");
    }
}
