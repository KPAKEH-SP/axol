package ru.lcp.axol.axolapi.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.lcp.axol.axolapi.profile.dto.SetProfileRequest;
import ru.lcp.axol.axolapi.profile.service.UserProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping()
    public ResponseEntity<?> getProfile(Authentication authentication) {
        return userProfileService.getProfile(authentication);
    }

    @GetMapping("/{userTag}")
    public ResponseEntity<?> getProfile(@PathVariable(name = "userTag") String userTag) {
        return userProfileService.getProfile(userTag);
    }

    @PostMapping()
    public ResponseEntity<?> setProfile(Authentication authentication, @RequestBody SetProfileRequest setProfileRequest) {
         return userProfileService.setProfile(authentication, setProfileRequest);
    }
}
