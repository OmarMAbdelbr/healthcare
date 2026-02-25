package com.kfh.healthcare.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOCTORS")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "English name is required")
    private String nameEn;

    @NotBlank(message = "Arabic name is required")
    private String nameAr;

    @NotBlank(message = "Specialty is required")
    private String specialty;

    private Integer yearsOfExperience;

    private Integer consultationDuration;
}
