package com.healthcare.healthcare_system.exception;

public class InvalidAppointmentStatusException extends RuntimeException{
    public InvalidAppointmentStatusException(String message){
        super(message);
    }
}
