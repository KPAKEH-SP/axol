package ru.lcp.axol.axolapi.events.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "events")
public class UserEvent {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "user-id")
    private UUID userId;

    @Column(name = "event-name")
    private String eventName;

    @Column(name = "event-start")
    private Long eventStart;

    @Column(name = "event-end")
    private Long eventEnd;
}
