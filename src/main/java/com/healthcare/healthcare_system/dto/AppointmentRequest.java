package com.healthcare.healthcare_system.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentRequest {

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDateTime appointmentDate;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
