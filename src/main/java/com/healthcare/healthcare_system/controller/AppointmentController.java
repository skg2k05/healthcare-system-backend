package com.healthcare.healthcare_system.controller;

import com.healthcare.healthcare_system.dto.AppointmentRequest;
import com.healthcare.healthcare_system.dto.AppointmentResponse;
import com.healthcare.healthcare_system.dto.AppointmentStatusRequest;
import com.healthcare.healthcare_system.exception.ResourceNotFoundException;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.DoctorRepository;
import com.healthcare.healthcare_system.repository.UserRepository;
import com.healthcare.healthcare_system.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    // CITIZEN → view own appointments
    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/my")
    public List<AppointmentResponse> myAppointments(Authentication authentication) {
        User patient = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appointmentService.getAppointmentsForPatient(patient);
    }

    // DOCTOR → view own appointments (paginated)
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/my")
    public Page<AppointmentResponse> doctorAppointments(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Doctor doctor = doctorRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentService.getAppointmentsForDoctor(doctor, page, size);
    }

    // DOCTOR → update appointment status (ONLY ONE METHOD)
    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping("/{id}/status")
    public AppointmentResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentStatusRequest request,
            Authentication authentication
    ) {
        Doctor doctor = doctorRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentService.updateAppointmentStatus(
                id,
                doctor,
                request.getStatus()
        );
    }

    // CITIZEN → create appointment
    @PreAuthorize("hasRole('CITIZEN')")
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody @Valid AppointmentRequest request,
            Authentication authentication
    ) {
        User patient = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        AppointmentResponse response =
                appointmentService.createAppointment(
                        patient,
                        doctor,
                        request.getAppointmentDate()
                );

        return ResponseEntity.ok(response);
    }
    // CITIZEN → cancel OWN appointment
    @PreAuthorize("hasRole('CITIZEN')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelByCitizen(@PathVariable Long id) {
        return ResponseEntity.ok(
                appointmentService.cancelByCitizen(id)
        );
    }
}
