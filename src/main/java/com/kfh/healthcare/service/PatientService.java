package com.kfh.healthcare.service;

import com.kfh.healthcare.dto.patient.PatientAppointmentDTO;
import com.kfh.healthcare.dto.patient.PatientRegistrationRequestDTO;
import com.kfh.healthcare.dto.patient.PatientRegistrationResponseDTO;
import com.kfh.healthcare.dto.patient.PatientWithAppointmentsDTO;
import com.kfh.healthcare.entity.Patient;
import com.kfh.healthcare.exception.BusinessValidationException;
import com.kfh.healthcare.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<PatientWithAppointmentsDTO> getAllPatientsWithAppointments() {
        return patientRepository.findAll().stream()
                .map(p -> new PatientWithAppointmentsDTO(
                        p.getId(),
                        p.getFullNameEn(),
                        p.getFullNameAr(),
                        p.getEmail(),
                        p.getMobileNumber(),
                        p.getDateOfBirth(),
                        p.getNationalId(),
                        p.getAddress(),
                        p.getAppointments().stream()
                                .map(a -> new PatientAppointmentDTO(
                                        a.getId(),
                                        a.getDoctor().getNameEn(),
                                        a.getAppointmentTime(),
                                        a.getDoctor().getSpecialty()
                                )).toList()
                )).toList();
    }

    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new BusinessValidationException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }
}
