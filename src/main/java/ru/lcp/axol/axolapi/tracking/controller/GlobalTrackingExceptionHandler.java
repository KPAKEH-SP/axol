package ru.lcp.axol.axolapi.tracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.lcp.axol.axolapi.tracking.exceptions.UserAlreadyTrackingException;
import ru.lcp.axol.axolapi.tracking.exceptions.UserNotTrackingException;

@ControllerAdvice
public class GlobalTrackingExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<?> catchUserAlreadyTrackingException(UserAlreadyTrackingException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler()
    public ResponseEntity<?> catchUserNotTrackingException(UserNotTrackingException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
