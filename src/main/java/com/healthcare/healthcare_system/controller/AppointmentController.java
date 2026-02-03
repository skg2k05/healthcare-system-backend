package com.healthcare.healthcare_system.controller;

import com.healthcare.healthcare_system.dto.AppointmentResponse;
import com.healthcare.healthcare_system.dto.AppointmentStatusRequest;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.DoctorRepository;
import com.healthcare.healthcare_system.repository.UserRepository;
import com.healthcare.healthcare_system.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentController(
            AppointmentService appointmentService,
            UserRepository userRepository,
            DoctorRepository doctorRepository
    ) {
        this.appointmentService = appointmentService;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    // ✅ Patient: my appointments
    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/my")
    public List<AppointmentResponse> myAppointments(Authentication authentication) {
        String email = authentication.getName();
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appointmentService.getAppointmentsForPatient(patient);
    }

    // ✅ Doctor: my appointments (PAGINATED)
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/my")
    public Page<AppointmentResponse> doctorAppointments(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        String email = authentication.getName();

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentService.getAppointmentsForDoctor(doctor, page, size);
    }

    // ✅ Doctor: update appointment status
    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping("/{id}/status")
    public AppointmentResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentStatusRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentService.updateAppointmentStatus(
                id,
                doctor,
                request.getStatus()
        );
    }
}
