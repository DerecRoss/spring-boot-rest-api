package com.rest_api.spring_boot_rest_api.exception.handler;

import com.rest_api.spring_boot_rest_api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice // any controller why call these exceptions the handler throw treatment
@RestController
public class CustomizeEntityResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // support for all exceptions for Exception.class
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception e, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(),
                webRequest.getDescription(true));
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RequiredObjectIsNonNullException.class) // support for all exceptions for Exception.class
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception e, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(),
                webRequest.getDescription(true));
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) // support for all exceptions for Exception.class
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(),
                webRequest.getDescription(true));
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class) // support for all exceptions for Exception.class
    public final ResponseEntity<ExceptionResponse> FileNotFoundException(Exception e, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(),
                webRequest.getDescription(true));
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileStorageException.class) // support for all exceptions for Exception.class
    public final ResponseEntity<ExceptionResponse> FileStorageException(Exception e, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(),
                webRequest.getDescription(true));
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
