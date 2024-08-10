package com.medilabo.patient.model;

import lombok.Getter;

@Getter
public enum Gender {
    M("Homme"),
    F("Femme");

    private final String description;

    Gender(String description) {
        this.description = description;
    }
}
