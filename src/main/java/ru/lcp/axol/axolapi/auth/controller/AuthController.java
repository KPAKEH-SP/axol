package ru.lcp.axol.axolapi.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lcp.axol.axolapi.auth.dto.AuthRequest;
import ru.lcp.axol.axolapi.auth.dto.RegisterRequest;
import ru.lcp.axol.axolapi.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
