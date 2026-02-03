package com.healthcare.healthcare_system.exception;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(String message){
        super(message);
    }
}
