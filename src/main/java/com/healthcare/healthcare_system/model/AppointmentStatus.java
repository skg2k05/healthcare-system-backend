package com.healthcare.healthcare_system.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//@Entity
//@Table
//public class AppointmentStatus {
//
//
//    //TODO [Reverse Engineering] generate columns from DB
//}

public enum AppointmentStatus{
    BOOKED,
    CANCELLED,
    COMPLETED
}