package com.medilabo.risk.controller;

import com.medilabo.risk.constant.RiskLevel;
import com.medilabo.risk.dto.NoteDto;
import com.medilabo.risk.dto.PatientDto;
import com.medilabo.risk.exception.PatientNotFoundException;
import com.medilabo.risk.exception.ServerErrorException;
import com.medilabo.risk.service.NotesService;
import com.medilabo.risk.service.PatientService;
import com.medilabo.risk.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RiskControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private NotesService notesService;

    private PatientDto mockPatient;
    private List<NoteDto> mockNotes;

    @BeforeEach
    void setUp() {
        mockPatient = new PatientDto();
        mockPatient.setId(1);
        mockPatient.setBirthDate(LocalDate.of(1966,12,31));
        mockPatient.setGender("F");

        NoteDto note = new NoteDto();
        note.setPatId(1);
        note.setNote("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");

        mockNotes = List.of(note);
    }

    @Test
    void testGetRiskById_Success() throws Exception {
        when(patientService.fetchPatientById(anyInt())).thenReturn(mockPatient);
        when(notesService.fetchNotesByPatId(anyInt())).thenReturn(mockNotes);

        mockMvc.perform(get("/risk/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.level", is("NONE")));
    }

    @Test
    void testGetRiskById_PatientNotFound() throws Exception {
        when(patientService.fetchPatientById(anyInt()))
                .thenThrow(new PatientNotFoundException("Patient not found"));

        mockMvc.perform(get("/risk/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Patient not found")));
    }

    @Test
    void testGetRiskById_ServerError() throws Exception {
        when(notesService.fetchNotesByPatId(anyInt()))
                .thenThrow(new ServerErrorException("Server notes error"));

        mockMvc.perform(get("/risk/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Server notes error")));
    }

    @Test
    void testGetRiskById_EmptyNotes() throws Exception {
        when(patientService.fetchPatientById(anyInt())).thenReturn(mockPatient);
        when(notesService.fetchNotesByPatId(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/risk/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level", is("NONE")));
    }
}