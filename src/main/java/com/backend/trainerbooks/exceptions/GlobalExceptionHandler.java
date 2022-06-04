package com.backend.trainerbooks.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    ResponseEntity<Object> handleException(AuthException ex){
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundEntityException.class)
    ResponseEntity<Object> handleNotFoundEntityException(NotFoundEntityException ex){
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}