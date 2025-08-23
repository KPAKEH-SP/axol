package ru.lcp.axol.axolapi.auth.exceptions.conflict;

public class UserWithThisEmailAlreadyExistException extends ConflictException {
    public UserWithThisEmailAlreadyExistException() {
        super("Пользователь с данной почтой уже существует");
    }
}
