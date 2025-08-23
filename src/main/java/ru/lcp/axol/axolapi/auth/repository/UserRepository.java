package ru.lcp.axol.axolapi.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lcp.axol.axolapi.auth.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserTag(String username);

    Optional<User> findByEmail(String email);
}
