package ru.lcp.axol.axolapi.tracking.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.lcp.axol.axolapi.auth.entity.User;

import java.util.UUID;

@Entity
@Table(name = "tracking")
@Data
public class Tracking {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watcher", referencedColumnName = "id")
    private User watcher;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking", referencedColumnName = "id")
    private User tracking;
}
