package ru.lcp.axol.axolapi.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lcp.axol.axolapi.events.entity.UserEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEventsRepository extends JpaRepository<UserEvent, UUID> {
    List<UserEvent> getUserEventByUserId(UUID userId);

    Optional<UserEvent> getUserEventByIdAndUserId(UUID id, UUID userId);
}
