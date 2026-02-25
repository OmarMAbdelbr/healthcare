package com.kfh.healthcare.controller;

import com.kfh.healthcare.dto.appintment.ScheduleAppointmentRequestDTO;
import com.kfh.healthcare.dto.appintment.ScheduleAppointmentResponseDTO;
import com.kfh.healthcare.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/${app.api.version}/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ScheduleAppointmentResponseDTO> schedule(@Valid @RequestBody ScheduleAppointmentRequestDTO request) {
        return new ResponseEntity<>(appointmentService.scheduleAppointment(request), HttpStatus.CREATED);
    }

}
