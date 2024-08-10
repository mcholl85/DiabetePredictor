package com.medilabo.patient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.medilabo.patient.exception.PatientNotFoundException;
import com.medilabo.patient.model.Gender;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
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
    public void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient createdPatient = patientService.createPatient(patient);

        verify(patientRepository).save(any(Patient.class));

        assertThat(createdPatient.getFirstName()).isEqualTo("Jean");
    }

    @Test
    public void testGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient foundPatient = patientService.getPatientById(1L);

        verify(patientRepository).findById(1L);

        assertThat(foundPatient).isNotNull();
        assertThat(foundPatient.getFirstName()).isEqualTo("Jean");
    }

    @Test
    public void testGetPatientById_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatientById(1L);
        });

        verify(patientRepository).findById(1L);
    }

    @Test
    public void testGetAllPatients() {
        Patient anotherPatient = Patient.builder()
                .firstName("Marie")
                .lastName("Curie")
                .birthDate(LocalDate.of(1867, 11, 7))
                .gender(Gender.F)
                .address("3 rue Pierre et Marie Curie")
                .phone("0654321987")
                .build();

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient, anotherPatient));

        List<Patient> patients = patientService.getAllPatients();

        assertThat(patients).hasSize(2).extracting(Patient::getFirstName).contains("Jean", "Marie");

        verify(patientRepository).findAll();
    }
    @Test
    public void testUpdatePatient() {
        Patient updatedPatient = Patient.builder()
                .firstName("Jean")
                .lastName("Moulin")
                .birthDate(LocalDate.of(1940, 5, 20))
                .gender(Gender.M)
                .address("2 rue de la République")
                .phone("0645678901")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient result = patientService.updatePatient(1L, updatedPatient);

        assertThat(result.getPhone()).isEqualTo("0645678901");

        verify(patientRepository).findById(1L);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testUpdatePatient_NotFound() {
        Patient updatedPatient = Patient.builder()
                .firstName("Jean")
                .lastName("Moulin")
                .birthDate(LocalDate.of(1940, 5, 20))
                .gender(Gender.M)
                .address("2 rue de la République")
                .phone("0645678901")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.updatePatient(1L, updatedPatient);
        });

        verify(patientRepository).findById(1L);
    }
}