package com.kfh.healthcare.dto.patient;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRegistrationRequestDTO(
        @NotBlank(message = "English Full Name is mandatory")
        String fullNameEn,

        @NotBlank(message = "Arabic Full Name is mandatory")
        String fullNameAr,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Mobile number is mandatory")
        String mobileNumber,

        @NotNull(message = "Date of Birth is mandatory")
        LocalDate dateOfBirth,

        @NotBlank(message = "National ID is mandatory")
        String nationalId,

        @NotBlank(message = "Address is mandatory")
        String address
) {}