package com.kfh.healthcare.service;

import com.kfh.healthcare.dto.appintment.ScheduleAppointmentRequestDTO;
import com.kfh.healthcare.dto.appintment.ScheduleAppointmentResponseDTO;
import com.kfh.healthcare.dto.appintment.UpdateAppointmentRequestDTO;
import com.kfh.healthcare.dto.appintment.UpdateAppointmentResponseDTO;
import com.kfh.healthcare.entity.Appointment;
import com.kfh.healthcare.entity.Doctor;
import com.kfh.healthcare.entity.Patient;
import com.kfh.healthcare.exception.BusinessValidationException;
import com.kfh.healthcare.repository.AppointmentRepository;
import com.kfh.healthcare.repository.DoctorRepository;
import com.kfh.healthcare.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Transactional
    public ScheduleAppointmentResponseDTO scheduleAppointment(ScheduleAppointmentRequestDTO request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new BusinessValidationException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new BusinessValidationException("Doctor not found"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(request.appointmentTime())
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new ScheduleAppointmentResponseDTO(
                savedAppointment.getId(),
                savedAppointment.getPatient().getId(),
                savedAppointment.getDoctor().getId(),
                savedAppointment.getAppointmentTime()
        );
    }

    @Transactional
    public UpdateAppointmentResponseDTO updateAppointment(Long id, UpdateAppointmentRequestDTO request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException("Appointment not found with ID: " + id));

        if (request.newTime() != null) {
            appointment.setAppointmentTime(request.newTime());
        }

        if (request.newDoctorId() != null) {
            Doctor newDoctor = doctorRepository.findById(request.newDoctorId())
                    .orElseThrow(() -> new BusinessValidationException("Doctor not found"));
            appointment.setDoctor(newDoctor);
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new UpdateAppointmentResponseDTO(
                savedAppointment.getId(),
                savedAppointment.getPatient().getId(),
                savedAppointment.getDoctor().getId(),
                savedAppointment.getAppointmentTime()
        );
    }

}
