package com.healthcare.healthcare_system.exception;

// UserNotFoundException.java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

