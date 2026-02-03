package com.healthcare.healthcare_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User patient;

    @ManyToOne
    private Doctor doctor;

    private LocalDate appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public User getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    // ===== SETTERS =====

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
