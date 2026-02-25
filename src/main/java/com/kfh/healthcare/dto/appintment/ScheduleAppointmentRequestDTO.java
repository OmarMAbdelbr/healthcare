package com.kfh.healthcare.dto.appintment;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleAppointmentRequestDTO(
        @NotNull(message = "Patient ID is required")
        Long patientId,

        @NotNull(message = "Doctor ID is required")
        Long doctorId,

        @NotNull(message = "Appointment time is required")
        LocalDateTime appointmentTime
) {
}
