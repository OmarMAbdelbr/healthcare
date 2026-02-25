package com.kfh.healthcare.dto.appintment;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record UpdateAppointmentRequestDTO(
        @Future(message = "New appointment time must be in the future")
        LocalDateTime newTime,
        Long newDoctorId
        ) {
}
