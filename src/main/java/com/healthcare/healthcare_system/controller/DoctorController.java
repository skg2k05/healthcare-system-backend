package com.healthcare.healthcare_system.controller;

import com.healthcare.healthcare_system.dto.DoctorSummaryResponse;
import com.healthcare.healthcare_system.repository.DoctorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public List<DoctorSummaryResponse> listDoctors() {
        return doctorRepository.findAll().stream()
                .sorted(Comparator.comparing(doctor -> doctor.getName().toLowerCase()))
                .map(doctor -> new DoctorSummaryResponse(
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getSpecialization()
                ))
                .toList();
    }
}
