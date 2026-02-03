package com.healthcare.healthcare_system.exception;

// DoctorNotFoundException.java
public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
