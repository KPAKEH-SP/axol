package ru.lcp.axol.axolapi.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lcp.axol.axolapi.friend.entity.Friend;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    Optional<Friend> findBySenderAndRecipient(UUID sender, UUID recipient);
    List<Friend> findAllBySender(UUID sender);
    List<Friend> findAllByRecipient(UUID recipient);
}
