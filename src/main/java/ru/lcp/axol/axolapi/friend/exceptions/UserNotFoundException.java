package ru.lcp.axol.axolapi.friend.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
