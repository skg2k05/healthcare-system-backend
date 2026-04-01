package com.healthcare.healthcare_system.dto;

public class DoctorSummaryResponse {

    private Long id;
    private String name;
    private String specialization;

    public DoctorSummaryResponse(Long id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}
