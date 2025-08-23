package ru.lcp.axol.axolapi.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String userTag;
    private String password;
}