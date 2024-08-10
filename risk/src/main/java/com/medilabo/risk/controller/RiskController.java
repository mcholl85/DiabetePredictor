package com.medilabo.risk.controller;

import com.medilabo.risk.constant.RiskLevel;
import com.medilabo.risk.dto.NoteDto;
import com.medilabo.risk.dto.PatientDto;
import com.medilabo.risk.dto.RiskDto;
import com.medilabo.risk.service.NotesService;
import com.medilabo.risk.service.PatientService;
import com.medilabo.risk.service.RiskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/risk")
public class RiskController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private NotesService notesService;

    @Autowired
    private RiskService riskService;

    @GetMapping("/{id}")
    public ResponseEntity<RiskDto> getRiskById(@PathVariable("id") Integer id) {
        List<NoteDto> notesDto = notesService.fetchNotesByPatId(id);
        PatientDto patient = patientService.fetchPatientById(id);

        List<String> notes = notesDto.stream().map(NoteDto::getNote).toList();

        RiskLevel riskLevel = riskService.getRiskLevelById(patient, notes);

        return ResponseEntity.ok(RiskDto.builder().id(id).level(riskLevel).build());
    }
}
