package ru.lcp.axol.axolapi.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lcp.axol.axolapi.auth.entity.CustomUserDetails;
import ru.lcp.axol.axolapi.auth.repository.UserRepository;
import ru.lcp.axol.axolapi.friend.exceptions.UserNotFoundException;
import ru.lcp.axol.axolapi.profile.dto.SetProfileRequest;
import ru.lcp.axol.axolapi.profile.entity.UserProfile;
import ru.lcp.axol.axolapi.profile.exceptions.ProfileNotFoundException;
import ru.lcp.axol.axolapi.profile.repository.UserProfileRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        return ResponseEntity.ok(getProfileById(userId));
    }

    public ResponseEntity<?> getProfile(String userTag) {
        // TODO: Integration with user/auth service
        UUID userId = userRepository.findByUserTag(userTag)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        // Continue profile service

        return ResponseEntity.ok(getProfileById(userId));
    }

    @Transactional
    public ResponseEntity<?> setProfile(Authentication authentication, SetProfileRequest setProfileRequest) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        Optional<UserProfile> profileOptional = userProfileRepository.findById(userId);

        UserProfile userProfile = profileOptional.orElseGet(UserProfile::new);

        userProfile.setUserId(userId);
        userProfile.setUsername(setProfileRequest.getUsername());
        userProfile.setDescription(setProfileRequest.getDescription());
        userProfileRepository.save(userProfile);

        return ResponseEntity.ok(HttpStatus.CREATED);

    }

    private UserProfile getProfileById (UUID userId) {
        return userProfileRepository.findById(userId).orElseThrow(ProfileNotFoundException::new);
    }
}
