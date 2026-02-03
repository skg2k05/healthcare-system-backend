package com.healthcare.healthcare_system.exception;

public class UnauthorizedDoctorException extends RuntimeException{
    public UnauthorizedDoctorException(String message){
        super(message);
    }
}
