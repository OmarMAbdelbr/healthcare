package com.kfh.healthcare.dto.appintment;


import java.time.LocalDateTime;

public record ScheduleAppointmentResponseDTO(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDateTime appointmentTime
) {
}
