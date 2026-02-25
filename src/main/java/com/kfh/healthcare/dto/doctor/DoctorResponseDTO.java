package com.kfh.healthcare.dto.doctor;

public record DoctorResponseDTO(
        Long id,

        String englishName,

        String arabicName,

        String specialty,

        Integer yearsOfExperience,

        Integer consultationDuration
) {
}
