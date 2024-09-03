package com.exalt.training.StudentRegistration.exception;

/**
 * Exception thrown when a n email is already used.
 */

public class EmailAlreadyInUseException extends RuntimeException {
    /**
     * Constructs a new EmailAlreadyInUseException with the specified detail message.
     *
     * @param message The detail message
     */
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
