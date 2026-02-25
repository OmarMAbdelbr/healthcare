package com.kfh.healthcare.controller;

import com.kfh.healthcare.dto.patient.PatientRegistrationRequestDTO;
import com.kfh.healthcare.dto.patient.PatientRegistrationResponseDTO;
import com.kfh.healthcare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
