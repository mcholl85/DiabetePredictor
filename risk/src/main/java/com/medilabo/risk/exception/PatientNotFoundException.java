package com.medilabo.risk.exception;

import java.io.Serial;

public class PatientNotFoundException  extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PatientNotFoundException(String message) {
        super(message);
    }
}