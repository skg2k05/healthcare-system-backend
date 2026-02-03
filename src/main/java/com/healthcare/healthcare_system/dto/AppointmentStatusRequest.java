package com.healthcare.healthcare_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AppointmentStatusRequest {

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "BOOKED|CANCELLED|COMPLETED",
            message = "Status must be BOOKED, CANCELLED, or COMPLETED"
    )
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
