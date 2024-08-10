package com.medilabo.risk.exception;

import java.io.Serial;

public class ServerErrorException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ServerErrorException(String message) {
        super(message);
    }
}
