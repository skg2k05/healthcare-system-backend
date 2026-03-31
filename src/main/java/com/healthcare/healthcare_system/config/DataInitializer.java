package com.healthcare.healthcare_system.config;

import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.DoctorRepository;
import com.healthcare.healthcare_system.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedDoctors() {
        seedDoctorIfNotExists("Dr. Sharma", "dr.sharma@test.com", "Cardiology");
        seedDoctorIfNotExists("Dr. Mehta", "dr.mehta@test.com", "Dermatology");
        seedDoctorIfNotExists("Dr. Iyer", "dr.iyer@test.com", "Neurology");

        seedOrUpdateDefaultUser("Kabir Patient", "kabir@test.com", "CITIZEN", "secret1234");
        seedOrUpdateDefaultUser("Dr. Sharma", "dr.sharma@test.com", "DOCTOR", "doctor123");

        System.out.println("Doctor seeding check completed.");
    }

    private void seedDoctorIfNotExists(String name, String email, String specialization) {
        if (doctorRepository.findByEmail(email).isEmpty()) {
            doctorRepository.save(new Doctor(name, email, specialization));
            System.out.println("Inserted: " + name);
        }
    }

    private void seedOrUpdateDefaultUser(String name, String email, String role, String rawPassword) {
        User user = userRepository.findByEmail(email).orElseGet(User::new);
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        System.out.println("Seeded/updated login user: " + email + " (" + role + ")");
    }
}
