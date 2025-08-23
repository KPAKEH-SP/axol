package ru.lcp.axol.axolapi.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
