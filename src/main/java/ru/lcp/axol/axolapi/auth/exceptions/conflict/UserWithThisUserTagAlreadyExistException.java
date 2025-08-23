package ru.lcp.axol.axolapi.auth.exceptions.conflict;

public class UserWithThisUserTagAlreadyExistException extends ConflictException {
    public UserWithThisUserTagAlreadyExistException() {
        super("Пользователь с данным тэгом уже существует");
    }
}
