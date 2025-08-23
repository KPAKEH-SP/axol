package ru.lcp.axol.axolapi.profile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.lcp.axol.axolapi.profile.exceptions.ProfileNotFoundException;

@ControllerAdvice
public class GlobalProfileExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<?> catchUserNotFoundException(ProfileNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
