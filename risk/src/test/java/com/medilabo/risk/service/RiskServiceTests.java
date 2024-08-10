package com.medilabo.risk.service;

import com.medilabo.risk.config.RiskProperties;
import com.medilabo.risk.constant.RiskLevel;
import com.medilabo.risk.dto.PatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class RiskServiceTests {
    private RiskService riskService;

    @Autowired
    private RiskProperties riskProperties;

    @BeforeEach
    void setUp() {
        riskService = new RiskService(riskProperties);
    }

    @Test
    void testGetRiskLevelById_None() {
        PatientDto patient = new PatientDto();
        patient.setBirthDate(LocalDate.of(1966, 12, 31));
        patient.setGender("F");

        List<String> notes = List.of("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");

        RiskLevel result = riskService.getRiskLevelById(patient, notes);
        assertEquals(RiskLevel.NONE, result);
    }

    @Test
    void testGetRiskLevelById_Borderline() {
        PatientDto patient = new PatientDto();
        patient.setBirthDate(LocalDate.of(1945, 6, 24));
        patient.setGender("M");

        List<String> notes = List.of("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");

        RiskLevel result = riskService.getRiskLevelById(patient, notes);
        assertEquals(RiskLevel.BORDERLINE, result);
    }

    @Test
    void testGetRiskLevelById_InDanger() {
        PatientDto patient = new PatientDto();
        patient.setBirthDate(LocalDate.of(2004, 6, 18));
        patient.setGender("M");

        List<String> notes = List.of("Le patient déclare qu'il fume depuis peu", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");

        RiskLevel result = riskService.getRiskLevelById(patient, notes);
        assertEquals(RiskLevel.IN_DANGER, result);
    }

    @Test
    void testGetRiskLevelById_EarlyOnset() {
        PatientDto patient = new PatientDto();
        patient.setBirthDate(LocalDate.of(2002, 6, 28));
        patient.setGender("F");

        List<String> notes = List.of("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé", "Taille, Poids, Cholestérol, Vertige et Réaction");

        RiskLevel result = riskService.getRiskLevelById(patient, notes);
        assertEquals(RiskLevel.EARLY_ONSET, result);
    }

}
