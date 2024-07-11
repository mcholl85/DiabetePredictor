package com.medilabo.patient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ -]+$", message = "FirstName must contain only letters, spaces and hyphens")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ -]+$", message = "LastName must contain only letters, spaces and hyphens")
    private String lastName;

    @NotNull
    @Past(message = "Date of birth must be in the past")
    private LocalDate birthDate;

    @NotNull
    private String gender;

    private String address;
    private String phone;
}
