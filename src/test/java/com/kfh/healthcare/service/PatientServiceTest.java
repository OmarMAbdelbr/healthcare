package com.kfh.healthcare.service;

import com.kfh.healthcare.dto.patient.PatientRegistrationRequestDTO;
import com.kfh.healthcare.dto.patient.PatientRegistrationResponseDTO;
import com.kfh.healthcare.dto.patient.PatientWithAppointmentsDTO;
import com.kfh.healthcare.entity.Appointment;
import com.kfh.healthcare.entity.Doctor;
import com.kfh.healthcare.entity.Patient;
import com.kfh.healthcare.exception.BusinessValidationException;
import com.kfh.healthcare.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void registerNewPatient_ShouldReturnSuccessResponse_WhenValidRequest() {
        when(patientRepository.existsByEmail(ArgumentMatchers.anyString())).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenAnswer(i -> {
            Patient patient = i.getArgument(0);
            patient.setId(1L); // Simulate DB ID generation
            return patient;
        });

        PatientRegistrationResponseDTO result = patientService.registerNewPatient(new PatientRegistrationRequestDTO(
                "Omar", "عمر", "omar@example.com",
                "0123456789", LocalDate.of(1990, 1, 1),
                "123456", "1st street Cairo"
        ));

        Assertions.assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Omar", result.fullNameEn());
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    void registerNewPatient_ShouldThrowBusinessValidationException_WhenEmailExists() {
        when(patientRepository.existsByEmail(ArgumentMatchers.anyString())).thenReturn(true);



        assertThrows(BusinessValidationException.class, () -> patientService.registerNewPatient(new PatientRegistrationRequestDTO(
                "Omar", "عمر", "omar@example.com",
                "0123456789", LocalDate.of(1990, 1, 1),
                "123456", "1st street Cairo"
        )));

        verify(patientRepository, never()).save(any());

    }

    @Test
    void getAllPatientsWithAppointments_EntitiesShouldMapToDto() {
        when(patientRepository.findAll()).thenReturn(
                new ArrayList<>(){{
                    add(Patient.builder()
                            .fullNameEn("Omar")
                            .appointments(
                                    new ArrayList<>(){{
                                        add(Appointment.builder()
                                                .patient(new Patient())
                                                .doctor(new Doctor())
                                                .appointmentTime(LocalDateTime.now())
                                                .build());
                                    }}
                            )
                            .build());
                }}
        );


        List<PatientWithAppointmentsDTO> result = patientService.getAllPatientsWithAppointments();

        Assertions.assertNotNull(result);
        assertEquals("Omar", result.getFirst().fullNameEn());
        assertThat(!result.getFirst().appointments().isEmpty());
    }
}
