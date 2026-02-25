package com.kfh.healthcare.dto.patient;

import java.time.LocalDate;
import java.util.List;

public record PatientWithAppointmentsDTO(
        Long id,
        String fullNameEn,
        String fullNameAr,
        String email,
        String mobileNumber,
        LocalDate dateOfBirth,
        String nationalId,
        String address,
        List<PatientAppointmentDTO> appointments
) {
}
