package ru.lcp.axol.axolapi.tracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.lcp.axol.axolapi.tracking.service.TrackingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracking")
public class TrackingController {
    private final TrackingService trackingService;

    @GetMapping()
    public ResponseEntity<?> getTrackingUsers(Authentication authentication) {
        return trackingService.getTrackingUsers(authentication);
    }

    @PostMapping("/{userTag}")
    public ResponseEntity<?> trackUser(Authentication authentication, @PathVariable String userTag) {
        return trackingService.trackUser(authentication, userTag);
    }

    @DeleteMapping("/{userTag}")
    public ResponseEntity<?> stopTrackingUser(Authentication authentication, @PathVariable String userTag) {
        return trackingService.stopTrackingUser(authentication, userTag);
    }
}
