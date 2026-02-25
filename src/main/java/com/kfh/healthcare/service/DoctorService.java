package com.kfh.healthcare.service;

import com.kfh.healthcare.dto.doctor.DoctorResponseDTO;
import com.kfh.healthcare.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Cacheable(value = "doctors")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctor -> new DoctorResponseDTO(
                        doctor.getId(),
                        doctor.getNameEn(),
                        doctor.getNameAr(),
                        doctor.getSpecialty(),
                        doctor.getYearsOfExperience(),
                        doctor.getConsultationDuration()
                ))
                .collect(Collectors.toList());
    }
}
