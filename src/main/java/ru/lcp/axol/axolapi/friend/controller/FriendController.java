package ru.lcp.axol.axolapi.friend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.lcp.axol.axolapi.friend.service.FriendService;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<?> getFriends(Authentication authentication) {
        return friendService.getFriends(authentication);
    }

    @PostMapping("/{userTag}")
    public ResponseEntity<?> addFriend(Authentication authentication, @PathVariable String userTag) {
        return friendService.addFriend(authentication, userTag);
    }

    @PutMapping("/{userTag}")
    public ResponseEntity<?> acceptFriend(Authentication authentication, @PathVariable String userTag) {
        return friendService.acceptFriend(authentication, userTag);
    }

    @DeleteMapping("/{userTag}")
    public ResponseEntity<?> removeFriend(Authentication authentication, @PathVariable String userTag) {
        return friendService.removeFriend(authentication, userTag);
    }
}
