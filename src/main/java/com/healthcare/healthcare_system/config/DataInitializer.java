package com.healthcare.healthcare_system.config;

import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.repository.DoctorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final DoctorRepository doctorRepository;

    public DataInitializer(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @PostConstruct
    public void seedDoctors() {

        if (doctorRepository.count() == 0) {

            doctorRepository.save(new Doctor(
                    "Dr. Sharma",
                    "dr.sharma@test.com",
                    "Cardiology"
            ));

            doctorRepository.save(new Doctor(
                    "Dr. Mehta",
                    "dr.mehta@test.com",
                    "Dermatology"
            ));

            doctorRepository.save(new Doctor(
                    "Dr. Iyer",
                    "dr.iyer@test.com",
                    "Neurology"
            ));

            System.out.println("Doctors seeded successfully.");
        }
    }
}