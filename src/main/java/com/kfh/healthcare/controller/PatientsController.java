package com.kfh.healthcare.controller;

import com.kfh.healthcare.dto.patient.PatientRegistrationRequestDTO;
import com.kfh.healthcare.dto.patient.PatientRegistrationResponseDTO;
import com.kfh.healthcare.dto.patient.PatientWithAppointmentsDTO;
import com.kfh.healthcare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/${app.api.version}/patients")
public class PatientsController {

    @Autowired
    PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientRegistrationResponseDTO> registerNewPatient(
            @RequestBody @Valid PatientRegistrationRequestDTO patientRegistrationRequestDTO
    ){
        PatientRegistrationResponseDTO responseDTO = patientService.registerNewPatient(patientRegistrationRequestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<PatientWithAppointmentsDTO>> getAllPatientsWithAppointments() {
        return ResponseEntity.ok(patientService.getAllPatientsWithAppointments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }

}
