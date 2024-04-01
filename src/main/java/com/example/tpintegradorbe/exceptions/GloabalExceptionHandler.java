package com.example.tpintegradorbe.exceptions;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloabalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GloabalExceptionHandler.class);
    @ExceptionHandler( ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException( ResourceNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(UniqueConstraintException.class)
    public ResponseEntity<String> handleUniqueConstraintException(UniqueConstraintException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
