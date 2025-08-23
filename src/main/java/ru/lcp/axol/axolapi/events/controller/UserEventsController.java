package ru.lcp.axol.axolapi.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.lcp.axol.axolapi.events.dto.UserEventRequest;
import ru.lcp.axol.axolapi.events.service.UserEventsService;

import java.util.UUID;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class UserEventsController {
    private final UserEventsService eventsService;

    @GetMapping
    public ResponseEntity<?> getUserEvents(Authentication authentication) {
        return eventsService.getUserEvents(authentication);
    }

    @GetMapping("/{userTag}")
    public ResponseEntity<?> getUserEvents(@PathVariable String userTag) {
        return eventsService.getUserEvents(userTag);
    }

    @PostMapping
    public ResponseEntity<?> addUserEvent(Authentication authentication, @RequestBody UserEventRequest event) {
        return eventsService.addUserEvent(authentication, event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteUserEvent(Authentication authentication, @PathVariable UUID eventId) {
        return eventsService.deleteUserEvent(authentication, eventId);
    }
}
