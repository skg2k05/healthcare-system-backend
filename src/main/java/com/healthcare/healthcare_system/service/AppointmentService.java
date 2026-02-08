package com.healthcare.healthcare_system.service;

import com.healthcare.healthcare_system.dto.AppointmentResponse;
import com.healthcare.healthcare_system.exception.InvalidAppointmentStatusException;
import com.healthcare.healthcare_system.exception.ResourceNotFoundException;
import com.healthcare.healthcare_system.exception.UnauthorizedActionException;
import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.AppointmentStatus;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // ✅ Patient view (unchanged)
    public List<AppointmentResponse> getAppointmentsForPatient(User patient) {
        return appointmentRepository.findByPatient(patient)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Doctor view (PAGINATED)
    public Page<AppointmentResponse> getAppointmentsForDoctor(
            Doctor doctor,
            int page,
            int size
    ) {
        return appointmentRepository
                .findByDoctor(doctor, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    // ✅ Status update
    public AppointmentResponse updateAppointmentStatus(
            Long appointmentId,
            Doctor doctor,
            String newStatus
    ) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found")
                );

        // Rule 1: Doctor ownership check
        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new UnauthorizedActionException(
                    "You cannot modify another doctor's appointment"
            );
        }

        AppointmentStatus currentStatus = appointment.getStatus();
        AppointmentStatus requestedStatus;

        try {
            requestedStatus = AppointmentStatus.valueOf(newStatus);
        } catch (IllegalArgumentException e) {
            throw new InvalidAppointmentStatusException(
                    "Invalid status value: " + newStatus
            );
        }

        // Rule 2: Cancelled- appointments are final
        if (currentStatus == AppointmentStatus.CANCELLED) {
            throw new InvalidAppointmentStatusException(
                    "Cancelled appointment cannot be updated"
            );
        }

        // Rule 3: Completed appointments are final
        if (currentStatus == AppointmentStatus.COMPLETED) {
            throw new InvalidAppointmentStatusException(
                    "Completed appointment cannot be updated"
            );
        }

        // Rule 4: Prevent invalid transitions
        if (currentStatus == AppointmentStatus.BOOKED &&
                requestedStatus == AppointmentStatus.BOOKED) {
            throw new InvalidAppointmentStatusException(
                    "Appointment is already booked"
            );
        }

        // All checks passed → update
        appointment.setStatus(requestedStatus);
        appointmentRepository.save(appointment);

        return mapToResponse(appointment);
    }


    // ✅ Mapper
    private AppointmentResponse mapToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();

        response.setId(appointment.getId());
        response.setStatus(appointment.getStatus().name());

        response.setDoctorId(appointment.getDoctor().getId());
        response.setDoctorName(appointment.getDoctor().getName());
        response.setSpecialization(appointment.getDoctor().getSpecialization());

        response.setPatientId(appointment.getPatient().getId());
        response.setPatientEmail(appointment.getPatient().getEmail());

        return response;
    }

    public AppointmentResponse createAppointment(
            User patient,
            Doctor doctor,
            LocalDateTime appointmentDate
    ) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.from(appointmentDate));
        appointment.setStatus(AppointmentStatus.BOOKED);

        appointmentRepository.save(appointment);

        return mapToResponse(appointment);
    }

    // 🧑‍⚕️ Doctor logic
    public Appointment updateStatusByDoctor(Long id, String newStatus) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new InvalidAppointmentStatusException("Completed appointment cannot be updated");
        }

        AppointmentStatus status;
        try {
            status = AppointmentStatus.valueOf(newStatus);
        } catch (Exception e) {
            throw new InvalidAppointmentStatusException("Invalid status value: " + newStatus);
        }

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    // 🧑 Citizen cancels OWN appointment
    public Appointment cancelByCitizen(Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName();

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // ownership check
        if (!appointment.getPatient().getEmail().equals(loggedInEmail)) {
            throw new UnauthorizedActionException("You can cancel only your own appointment");
        }

        // status check
        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new InvalidAppointmentStatusException("Only BOOKED appointments can be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }

}
