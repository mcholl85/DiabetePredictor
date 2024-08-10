package com.medilabo.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patient.config.TestSecurityConfig;
import com.medilabo.patient.model.Gender;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = Patient.builder()
                .firstName("Jean")
                .lastName("Moulin")
                .birthDate(LocalDate.of(1940, 5, 20))
                .gender(Gender.M)
                .address("2 rue de la République")
                .phone("0645678900")
                .build();
    }

    @Test
    public void testCreatePatient() throws Exception {
        Mockito.when(patientService.createPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jean"));
    }

    @Test
    public void testGetPatientById() throws Exception {
        Mockito.when(patientService.getPatientById(anyLong())).thenReturn(patient);

        mockMvc.perform(get("/patients/1")
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

        List<Patient> patients = Arrays.asList(patient, anotherPatient);
        Mockito.when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jean"))
                .andExpect(jsonPath("$[1].firstName").value("Marie"));
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

        Mockito.when(patientService.updatePatient(anyLong(), any(Patient.class))).thenReturn(updatedPatient);

        mockMvc.perform(put("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Valjean"));
    }

}
