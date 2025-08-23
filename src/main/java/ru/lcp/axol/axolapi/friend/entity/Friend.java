package ru.lcp.axol.axolapi.friend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "friends")
public class Friend {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID sender;
    private UUID recipient;
    private Boolean accepted = false;
}
