package com.healthcare.healthcare_system.repository;

import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Patient (no pagination needed now)
    List<Appointment> findByPatient(User patient);

    // Doctor — PAGINATED
    Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);
}
