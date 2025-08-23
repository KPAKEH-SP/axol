package ru.lcp.axol.axolapi.events.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.lcp.axol.axolapi.events.exceptions.notfound.UserEventNotFoundException;

@ControllerAdvice
public class GlobalEventExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<?> catchUserNotFoundException(UserEventNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
