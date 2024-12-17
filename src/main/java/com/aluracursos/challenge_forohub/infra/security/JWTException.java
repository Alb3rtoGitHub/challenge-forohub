package com.aluracursos.challenge_forohub.infra.security;

public class JWTException extends RuntimeException {
    public JWTException(String message) {
        super(message);
    }

    public JWTException(String message, Throwable cause) {
        super(message, cause);
    }
}
