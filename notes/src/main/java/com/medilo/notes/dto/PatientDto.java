package com.medilo.notes.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private String phone;
}
