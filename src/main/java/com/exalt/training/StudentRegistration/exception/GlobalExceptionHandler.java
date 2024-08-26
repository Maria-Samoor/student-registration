package com.exalt.training.StudentRegistration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling specific exceptions across the application.
 */

public class GlobalExceptionHandler {

    /**
     * Handles StudentNotFoundException and returns an appropriate response.
     *
     * @param ex The exception that was thrown
     * @return ResponseEntity containing the error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> handleEmailAlreadyUsedException(StudentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles EmailAlreadyInUseException and returns an appropriate response.
     *
     * @param ex The exception that was thrown
     * @return ResponseEntity containing the error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
