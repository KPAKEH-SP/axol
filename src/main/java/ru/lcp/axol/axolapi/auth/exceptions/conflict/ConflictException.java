package ru.lcp.axol.axolapi.auth.exceptions.conflict;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
