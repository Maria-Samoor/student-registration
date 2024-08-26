package com.exalt.training.StudentRegistration.exception;

/**
 * Exception thrown when a student is not found in the system.
 */
public class StudentNotFoundException extends RuntimeException  {

    /**
     * Constructs a new StudentNotFoundException with the specified detail message.
     *
     * @param message The detail message
     */
    public StudentNotFoundException(String message) {
        super(message);
    }
}
