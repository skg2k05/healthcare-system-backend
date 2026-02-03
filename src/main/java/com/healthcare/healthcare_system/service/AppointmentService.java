package com.healthcare.healthcare_system.service;

import com.healthcare.healthcare_system.dto.AppointmentResponse;
import com.healthcare.healthcare_system.exception.InvalidAppointmentStatusException;
import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.AppointmentStatus;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("Unauthorized doctor access");
        }

        AppointmentStatus current = appointment.getStatus();
        AppointmentStatus next = AppointmentStatus.valueOf(newStatus);

        if (current == AppointmentStatus.COMPLETED) {
            throw new InvalidAppointmentStatusException(
                    "Invalid status transition from COMPLETED to " + next
            );
        }

        appointment.setStatus(next);
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
}
