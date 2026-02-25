package com.kfh.healthcare.dto.appintment;

import java.time.LocalDateTime;

public record UpdateAppointmentResponseDTO(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDateTime appointmentTime
) {
}
