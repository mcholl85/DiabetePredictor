package com.medilabo.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patient.config.TestSecurityConfig;
import com.medilabo.patient.model.Gender;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patientRepository.deleteAll();

        patient = Patient.builder()
                .firstName("Jean")
                .lastName("Moulin")
                .birthDate(LocalDate.of(1940, 5, 20))
                .gender(Gender.M)
                .address("2 rue de la République")
                .phone("0645678900")
                .build();

        patient = patientRepository.save(patient);
    }

    @Test
    public void testCreatePatient() throws Exception {
        Patient newPatient = Patient.builder()
                .firstName("Marie")
                .lastName("Curie")
                .birthDate(LocalDate.of(1867, 11, 7))
                .gender(Gender.F)
                .address("3 rue Pierre et Marie Curie")
                .phone("0654321987")
                .build();

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Marie"));
    }

    @Test
    public void testGetPatientById() throws Exception {
        mockMvc.perform(get("/patients/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jean"));
    }

    @Test
    public void testGetAllPatients() throws Exception {
        Patient anotherPatient = Patient.builder()
                .firstName("Marie")
                .lastName("Curie")
                .birthDate(LocalDate.of(1867, 11, 7))
                .gender(Gender.F)
                .address("3 rue Pierre et Marie Curie")
                .phone("0654321987")
                .build();

        patientRepository.save(anotherPatient);

        mockMvc.perform(get("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Jean"))
                .andExpect(jsonPath("$.content[1].firstName").value("Marie"));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Patient updatedPatient = Patient.builder()
                .firstName("Jean")
                .lastName("Valjean")
                .birthDate(LocalDate.of(1940, 5, 20))
                .gender(Gender.M)
                .address("2 rue de la République")
                .phone("0645678900")
                .build();

        mockMvc.perform(put("/patients/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Valjean"));
    }
}