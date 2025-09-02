package ru.lcp.axol.axolapi.tracking.exceptions;

public class UserAlreadyTrackingException extends RuntimeException {
    public UserAlreadyTrackingException() {
        super("Вы уже отслеживаете данного пользователя");
    }
}
