package ru.lcp.axol.axolapi.events.exceptions.notfound;

public class UserEventNotFoundException extends RuntimeException {
    public UserEventNotFoundException() {
        super("Указанное событие не найдено");
    }
}
