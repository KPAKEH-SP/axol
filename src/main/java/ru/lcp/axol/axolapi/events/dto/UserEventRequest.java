package ru.lcp.axol.axolapi.events.dto;

import lombok.Data;

@Data
public class UserEventRequest {
    private String eventName;
    private Long eventStartDate;
    private Long eventEndDate;
}
