package com.kfh.healthcare.service;

import com.kfh.healthcare.dto.patient.PatientRegistrationRequestDTO;
import com.kfh.healthcare.dto.patient.PatientRegistrationResponseDTO;
import com.kfh.healthcare.entity.Patient;
import com.kfh.healthcare.exception.BusinessValidationException;
import com.kfh.healthcare.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Transactional
    public PatientRegistrationResponseDTO registerNewPatient(PatientRegistrationRequestDTO request) {
        if (patientRepository.existsByEmail(request.email())) {
            throw new BusinessValidationException("A patient with the same email " + request.email() + " already exists.");
        }

        Patient patient = Patient.builder()
                .fullNameEn(request.fullNameEn())
                .fullNameAr(request.fullNameAr())
                .email(request.email())
                .mobileNumber(request.mobileNumber())
                .dateOfBirth(request.dateOfBirth())
                .nationalId(request.nationalId())
                .address(request.address())
                .build();

        Patient savedPatient = patientRepository.save(patient);

        return new PatientRegistrationResponseDTO(
                savedPatient.getId(),
                savedPatient.getFullNameEn(),
                savedPatient.getFullNameAr(),
                savedPatient.getEmail(),
                savedPatient.getMobileNumber(),
                savedPatient.getDateOfBirth(),
                savedPatient.getNationalId(),
                savedPatient.getAddress()
        );
    }
}
