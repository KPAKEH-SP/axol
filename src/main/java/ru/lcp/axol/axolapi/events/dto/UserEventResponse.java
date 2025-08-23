package ru.lcp.axol.axolapi.events.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserEventResponse {
    private UUID id;
    private String eventName;
    private Long eventStartDate;
    private Long eventEndDate;
}
