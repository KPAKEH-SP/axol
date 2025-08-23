package ru.lcp.axol.axolapi.profile.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException() {
        super("Профиль не найден");
    }
}
