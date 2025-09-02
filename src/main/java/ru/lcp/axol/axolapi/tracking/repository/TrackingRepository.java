package ru.lcp.axol.axolapi.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lcp.axol.axolapi.auth.entity.User;
import ru.lcp.axol.axolapi.tracking.entity.Tracking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, UUID> {
    List<Tracking> getTrackingUserByWatcherId(UUID watcherId);

    Optional<Tracking> getTrackingByWatcherAndTracking(User watcher, User tracking);
}
