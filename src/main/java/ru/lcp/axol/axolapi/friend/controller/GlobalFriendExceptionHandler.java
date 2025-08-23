package ru.lcp.axol.axolapi.friend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.lcp.axol.axolapi.friend.exceptions.FriendRequestNotFoundException;
import ru.lcp.axol.axolapi.friend.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalFriendExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<?> catchUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    public ResponseEntity<?> catchUserNotFoundException(FriendRequestNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
