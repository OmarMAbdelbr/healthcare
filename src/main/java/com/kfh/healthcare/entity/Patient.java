package com.kfh.healthcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.util.List;

@SoftDelete
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PATIENTS")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "English name is required")
    private String fullNameEn;

    @NotBlank(message = "Arabic name is required")
    private String fullNameAr;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "National ID is required")
    @Column(unique = true, nullable = false)
    private String nationalId;

    @NotBlank(message = "Address is required")
    @Column( nullable = false)
    private String address;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

}
