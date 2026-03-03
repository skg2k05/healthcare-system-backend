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

        seedDoctorIfNotExists("Dr. Sharma", "dr.sharma@test.com", "Cardiology");
        seedDoctorIfNotExists("Dr. Mehta", "dr.mehta@test.com", "Dermatology");
        seedDoctorIfNotExists("Dr. Iyer", "dr.iyer@test.com", "Neurology");

        System.out.println("Doctor seeding check completed.");
    }

    private void seedDoctorIfNotExists(String name, String email, String specialization) {
        if (doctorRepository.findByEmail(email).isEmpty()) {
            doctorRepository.save(new Doctor(name, email, specialization));
            System.out.println("Inserted: " + name);
        }
    }
}