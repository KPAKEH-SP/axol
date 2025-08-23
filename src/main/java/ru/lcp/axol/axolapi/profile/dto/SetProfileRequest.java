package ru.lcp.axol.axolapi.profile.dto;

import lombok.Data;

@Data
public class SetProfileRequest {
    private String username;
    private String description;
}
