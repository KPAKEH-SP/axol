package ru.lcp.axol.axolapi.tracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lcp.axol.axolapi.auth.entity.CustomUserDetails;
import ru.lcp.axol.axolapi.auth.entity.User;
import ru.lcp.axol.axolapi.auth.repository.UserRepository;
import ru.lcp.axol.axolapi.friend.exceptions.UserNotFoundException;
import ru.lcp.axol.axolapi.tracking.entity.Tracking;
import ru.lcp.axol.axolapi.tracking.exceptions.UserAlreadyTrackingException;
import ru.lcp.axol.axolapi.tracking.exceptions.UserNotTrackingException;
import ru.lcp.axol.axolapi.tracking.repository.TrackingRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {
    private final TrackingRepository trackingRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getTrackingUsers(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        List<String> trackingTags = trackingRepository.getTrackingUserByWatcherId(userId).stream().map(tracking ->
                tracking.getTracking().getUserTag()).toList();

        return ResponseEntity.ok(trackingTags);
    }

    @Transactional
    public ResponseEntity<?> trackUser(Authentication authentication, String userTag) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID watcherId = userDetails.getId();

        // TODO: Integration with user/auth service
        User watcher = userRepository.findById(watcherId)
                .orElseThrow(UserNotFoundException::new);

        User trackingUser = userRepository.findByUserTag(userTag)
                .orElseThrow(UserNotFoundException::new);

        // Continue tracking service

        if (trackingRepository.getTrackingByWatcherAndTracking(watcher, trackingUser).isPresent()) {
            throw new UserAlreadyTrackingException();
        }

        Tracking tracking = new Tracking();

        tracking.setWatcher(watcher);
        tracking.setTracking(trackingUser);

        return ResponseEntity.ok(trackingUser.getUserTag());
    }

    @Transactional
    public ResponseEntity<?> stopTrackingUser(Authentication authentication, String userTag) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID watcherId = userDetails.getId();

        // TODO: Integration with user/auth service
        User watcher = userRepository.findById(watcherId)
                .orElseThrow(UserNotFoundException::new);

        User trackingUser = userRepository.findByUserTag(userTag)
                .orElseThrow(UserNotFoundException::new);

        // Continue tracking service

        Tracking tracking = trackingRepository.getTrackingByWatcherAndTracking(watcher, trackingUser)
                .orElseThrow(UserNotTrackingException::new);

        trackingRepository.delete(tracking);

        return ResponseEntity.ok(userTag);
    }
}
