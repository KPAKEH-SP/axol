package ru.lcp.axol.axolapi.friend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lcp.axol.axolapi.auth.entity.CustomUserDetails;
import ru.lcp.axol.axolapi.auth.repository.UserRepository;
import ru.lcp.axol.axolapi.friend.entity.Friend;
import ru.lcp.axol.axolapi.friend.exceptions.FriendRequestNotFoundException;
import ru.lcp.axol.axolapi.friend.exceptions.UserNotFoundException;
import ru.lcp.axol.axolapi.friend.repository.FriendRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getFriends(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<Friend> friends = friendRepository.findAllBySender(userDetails.getId());
        friends.addAll(friendRepository.findAllByRecipient(userDetails.getId()));

        return ResponseEntity.ok(friends);
    }

    @Transactional
    public ResponseEntity<?> addFriend(Authentication authentication, String recipientTag) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // TODO: Integration with user/auth service
        UUID recipientId = userRepository.findByUserTag(recipientTag)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        // Continue friend service logic
        if (friendRepository.findBySenderAndRecipient(userDetails.getId(), recipientId).isPresent() ||
                friendRepository.findBySenderAndRecipient(recipientId, userDetails.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already in friend list");
        }

        Friend friend = new Friend();
        friend.setSender(userDetails.getId());
        friend.setRecipient(recipientId);
        friendRepository.save(friend);

        return ResponseEntity.status(HttpStatus.CREATED).body(friend);
    }

    @Transactional
    public ResponseEntity<?> acceptFriend(Authentication authentication, String senderTag) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // TODO: Integration with user/auth service
        UUID senderId = userRepository.findByUserTag(senderTag)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        // Continue friend service logic
        Friend friendRequest = friendRepository.findBySenderAndRecipient(senderId, userDetails.getId())
                .orElseThrow(FriendRequestNotFoundException::new);

        friendRequest.setAccepted(true);
        friendRepository.save(friendRequest);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(friendRequest);
    }

    public ResponseEntity<?> removeFriend(Authentication authentication, String userTag) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // TODO: Integration with user/auth service
        UUID userId = userRepository.findByUserTag(userTag)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        // Continue friend service logic
        friendRepository.findBySenderAndRecipient(userId, userDetails.getId()).ifPresent(friendRepository::delete);
        friendRepository.findBySenderAndRecipient(userDetails.getId(), userId).ifPresent(friendRepository::delete);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User removed from friend list");
    }
}
