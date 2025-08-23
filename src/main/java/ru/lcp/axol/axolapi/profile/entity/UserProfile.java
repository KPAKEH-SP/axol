package ru.lcp.axol.axolapi.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "profiles")
public class UserProfile {

    @Id
    @Column(name = "user_id", columnDefinition = "uuid")
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(name = "description")
    private String description;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
