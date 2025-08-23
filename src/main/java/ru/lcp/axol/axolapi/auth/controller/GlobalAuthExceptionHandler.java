package ru.lcp.axol.axolapi.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.lcp.axol.axolapi.auth.exceptions.conflict.ConflictException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalAuthExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> catchUserNotFoundException(ConflictException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
