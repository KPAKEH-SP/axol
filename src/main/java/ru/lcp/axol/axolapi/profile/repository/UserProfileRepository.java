package ru.lcp.axol.axolapi.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lcp.axol.axolapi.profile.entity.UserProfile;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findById(UUID id);
}
