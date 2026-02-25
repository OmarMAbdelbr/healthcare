package com.kfh.healthcare.dto.patient;

import java.time.LocalDateTime;

public record PatientAppointmentDTO(
        Long id,
        String doctorNameEn,
        LocalDateTime appointmentTime,
        String specialty
) {
}
