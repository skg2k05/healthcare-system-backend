package com.healthcare.healthcare_system.dto;

public class AppointmentResponse {

    private Long id;
    private String status;

    private Long doctorId;
    private String doctorName;
    private String specialization;

    private Long patientId;
    private String patientEmail;

    // getter

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }
    //  setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}


