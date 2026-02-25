package com.kfh.healthcare.controller;

import com.kfh.healthcare.dto.doctor.DoctorResponseDTO;
import com.kfh.healthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/${app.api.version}/doctors")
public class DoctorsController {

    @Autowired
    DoctorService doctorService;

    @GetMapping()
    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

}
