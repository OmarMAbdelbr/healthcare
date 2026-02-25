package com.kfh.healthcare.dto.patient;

import java.time.LocalDate;

public record PatientRegistrationResponseDTO(
        Long id,
        String fullNameEn,
        String fullNameAr,
        String email,
        String mobileNumber,
        LocalDate dateOfBirth,
        String nationalId,
        String address
) {}