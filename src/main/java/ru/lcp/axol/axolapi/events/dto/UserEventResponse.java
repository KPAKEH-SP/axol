package ru.lcp.axol.axolapi.events.dto;

import lombok.Data;
import ru.lcp.axol.axolapi.events.entity.UserEvent;

import java.util.UUID;

@Data
public class UserEventResponse {
    private UUID id;
    private String eventName;
    private Long eventStartDate;
    private Long eventEndDate;

    public UserEventResponse(UserEvent userEvent) {
        this.id = userEvent.getId();
        this.eventName = userEvent.getEventName();
        this.eventStartDate = userEvent.getEventStart();
        this.eventEndDate = userEvent.getEventEnd();
    }
}
