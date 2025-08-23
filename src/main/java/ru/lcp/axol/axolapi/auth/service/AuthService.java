package ru.lcp.axol.axolapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lcp.axol.axolapi.auth.config.filters.JwtProvider;
import ru.lcp.axol.axolapi.auth.dto.AuthRequest;
import ru.lcp.axol.axolapi.auth.dto.RegisterRequest;
import ru.lcp.axol.axolapi.auth.entity.User;
import ru.lcp.axol.axolapi.auth.exceptions.conflict.UserWithThisEmailAlreadyExistException;
import ru.lcp.axol.axolapi.auth.exceptions.conflict.UserWithThisUserTagAlreadyExistException;
import ru.lcp.axol.axolapi.auth.repository.RoleRepository;
import ru.lcp.axol.axolapi.auth.repository.UserRepository;
import ru.lcp.axol.axolapi.profile.entity.UserProfile;
import ru.lcp.axol.axolapi.profile.repository.UserProfileRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public ResponseEntity<?> login(AuthRequest request) {
        return ResponseEntity.ok(getTokenByUserTagAndPassword(request.getUserTag(), request.getPassword()));
    }

    @Transactional
    public ResponseEntity<?> register(RegisterRequest request) {
        userRepository.findByUserTag(request.getUserTag()).ifPresent(user -> {
            throw new UserWithThisUserTagAlreadyExistException();
        });

        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new UserWithThisEmailAlreadyExistException();
        });

        User user = new User();
        user.setUserTag(request.getUserTag());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(Set.of(
                roleRepository.findByName("ROLE_USER").orElseThrow()
        ));
        userRepository.save(user);

        /// Integration with profile service

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        userProfile.setUsername(user.getUserTag());
        userProfileRepository.save(userProfile);

        /// Continue main logic

        return ResponseEntity.status(HttpStatus.CREATED).body(getTokenByUserTagAndPassword(user.getUserTag(), request.getPassword()));
    }

    private String getTokenByUserTagAndPassword(String userTag, String password) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userTag, password)
        );
        return jwtProvider.generateToken(auth);
    }
}
