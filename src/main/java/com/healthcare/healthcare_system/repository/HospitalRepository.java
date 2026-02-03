package com.healthcare.healthcare_system.repository;

import com.healthcare.healthcare_system.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
