package ru.axol.authservice.dto;

import lombok.Data;

@Data
public class UserCredentialDto {
    private String userTag;
    private String password;
}
