package com.medilabo.risk.service;

import com.medilabo.risk.config.RiskProperties;
import com.medilabo.risk.constant.RiskLevel;
import com.medilabo.risk.dto.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RiskService {
    private final List<String> terms;

    @Autowired
    public RiskService(RiskProperties riskProperties) {
        this.terms = riskProperties.getTerms();
    }

    public RiskLevel getRiskLevelById(PatientDto patient, List<String> notes) {
        int age = calculateAge(patient.getBirthDate());
        int count = countTerms(notes);

        if (isBordelineRisk(age, count))
            return RiskLevel.BORDERLINE;

        if (isInDangerRisk(age, count, patient))
            return RiskLevel.IN_DANGER;

        if (isEarlyOnsetRisk(age, count, patient))
            return RiskLevel.EARLY_ONSET;

        return RiskLevel.NONE;
    }

    private boolean isBordelineRisk(int age, int count) {
        return count >= 2 && count <= 5 && age > 30;
    }

    private boolean isInDangerRisk(int age, int count, PatientDto patient) {
        if (age <= 30) {
            if (patient.getGender().equals("M") && count >= 3 && count < 5) {
                return true;
            } else
                return patient.getGender().equals("F") && count >= 4 && count < 7;
        } else {
            return count >= 6 && count <= 7;
        }
    }

    private boolean isEarlyOnsetRisk(int age, int count, PatientDto patient) {
        if (age <= 30) {
            if (patient.getGender().equals("M") && count >= 5) {
                return true;
            } else
                return patient.getGender().equals("F") && count >= 7;
        } else {
            return count >= 8;
        }
    }

    private int countTerms(List<String> notes) {
        return notes
                .stream()
                .flatMap(note -> terms
                        .stream()
                        .filter(term -> note
                                .toLowerCase()
                                .contains(term.toLowerCase())))
                .reduce(0, (count, term) -> count + 1, Integer::sum);
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();

        return Period.between(birthDate, today).getYears();
    }
}
