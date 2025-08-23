package ru.lcp.axol.axolapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lcp.axol.axolapi.auth.entity.CustomUserDetails;
import ru.lcp.axol.axolapi.auth.entity.User;
import ru.lcp.axol.axolapi.auth.repository.UserRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userTag) throws UsernameNotFoundException {
        User user = userRepository.findByUserTag(userTag)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userTag));

        return new CustomUserDetails(
                user.getId(),
                user.getUserTag(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );
    }

    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by id: " + id));

        return new CustomUserDetails(
                user.getId(),
                user.getUserTag(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );
    }
}
