package ru.lcp.axol.axolapi.friend.exceptions;

public class FriendRequestNotFoundException extends RuntimeException {
    public FriendRequestNotFoundException() {
        super("Friend request not found");
    }
}
