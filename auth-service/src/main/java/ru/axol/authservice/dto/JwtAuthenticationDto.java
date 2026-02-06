package ru.axol.authservice.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {
    private String accessToken;
    private String refreshToken;
}
