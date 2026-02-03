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

    @Column(unique = true, nullable =false)
    private String email;

    @NotBlank
    private String specialization;

    @ManyToOne
    @JoinColumn(name ="hospital_id")
    private Hospital hospital;

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSpecialization(){
        return specialization;
    }
    public String getEmail(){ return email; }

}
