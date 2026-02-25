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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void scheduleAppointment_shouldReturnSuccess_WhenPatientAndDoctorExists(){
        Patient patient = Patient.builder().id(1L).build();
        Doctor doctor = Doctor.builder().id(2L).build();
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.ofNullable(doctor));
        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(Appointment.builder().id(3L).patient(patient).doctor(doctor).build());

        ScheduleAppointmentResponseDTO result = appointmentService.scheduleAppointment(new ScheduleAppointmentRequestDTO(
                1L,2L, LocalDateTime.now()
        ));

        Assertions.assertNotNull(result);
        assertEquals(1L, result.patientId());
        assertEquals(2L, result.doctorId());
        assertEquals(3L, result.id());
        verify(appointmentRepository, times(1)).save(any());
    }

    @Test
    void scheduleAppointment_shouldThrow_WhenPatientDoesNotExist(){
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessValidationException.class, () -> appointmentService.scheduleAppointment(new ScheduleAppointmentRequestDTO(
                1L,2L, LocalDateTime.now()
        )));
    }

    @Test
    void updateAppointment_shouldUpdateTimeAndDoctor_WithValidData(){
        Appointment appointment = Appointment.builder()
                .id(1L)
                .doctor(Doctor.builder().id(1L).build())
                .patient(Patient.builder().id(1L).build())
                .build();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.ofNullable(appointment));

        when(doctorRepository.findById(2L)).thenReturn(Optional.ofNullable(Doctor.builder().id(2L).nameEn("Omar").build()));

        when(appointmentRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        UpdateAppointmentResponseDTO result = appointmentService.updateAppointment(1L,new UpdateAppointmentRequestDTO(
                LocalDateTime.now().plusDays(10),2L
        ));

        assertEquals(2L,result.doctorId());
    }

    @Test
    void updateAppointment_shouldThrow_WhenDoctorDoesNotExist(){
        when(appointmentRepository.findById(1L)).thenReturn(Optional.ofNullable(Appointment.builder().id(3L).build()));
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessValidationException.class, () -> appointmentService.updateAppointment(1L,new UpdateAppointmentRequestDTO(
                LocalDateTime.now().plusDays(10),1L
        )));
    }
}
