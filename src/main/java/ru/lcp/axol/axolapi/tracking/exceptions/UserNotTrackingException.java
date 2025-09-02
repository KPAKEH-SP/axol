package ru.lcp.axol.axolapi.tracking.exceptions;

public class UserNotTrackingException extends RuntimeException {
    public UserNotTrackingException() {
        super("Вы не отслеживаете данного пользователя");
    }
}
