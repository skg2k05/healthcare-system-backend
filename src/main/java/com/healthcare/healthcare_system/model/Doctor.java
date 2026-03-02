package com.healthcare.healthcare_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String specialization;

    @ManyToOne
    @JoinColumn(name ="hospital_id")
    private Hospital hospital;

    // Default constructor (Required by JPA)
    public Doctor() {}

    // Constructor for easy object creation
    public Doctor(String name, String email, String specialization) {
        this.name = name;
        this.email = email;
        this.specialization = specialization;
    }

    // Getters
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSpecialization(){
        return specialization;
    }

    public String getEmail(){
        return email;
    }

    // Setters (Recommended)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}