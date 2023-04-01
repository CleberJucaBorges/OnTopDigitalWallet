package com.ontopchallenge.ontopdigitalwallet.Exception.Handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        Throwable rootCause = getRootCause(ex);
        String errorMessage = "An error occurred while processing your request";
        if (rootCause != null) {
            errorMessage += ": " + rootCause.getMessage();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    private Throwable getRootCause(Throwable ex) {
        Throwable cause = ex.getCause();
        if (cause != null) {
            return getRootCause(cause);
        }
        return ex;
    }
}
