package ru.axol.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.axol.authservice.dto.JwtAuthenticationDto;
import ru.axol.authservice.dto.RefreshTokenDto;
import ru.axol.authservice.dto.UserCredentialDto;
import ru.axol.authservice.dto.UserDto;
import ru.axol.authservice.entity.User;
import ru.axol.authservice.repository.UserRepository;
import ru.axol.authservice.security.jwt.JwtService;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDto signIn(UserCredentialDto userCredentialDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialDto);
        return jwtService.generateAuthenticationToken(user.getUserTag());
    }

    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateToken(refreshToken)) {
            User user = findByUserTag(jwtService.getUserTag(refreshToken));
            return jwtService.refreshAccessToken(user.getUserTag(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Transactional
    public JwtAuthenticationDto addUser(UserDto userDto) {
        User user = new User();
        user.setUserTag(userDto.getUserTag());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return jwtService.generateAuthenticationToken(user.getUserTag());
    }

    private User findByCredentials(UserCredentialDto userCredentialDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findUserByUserTag(userCredentialDto.getUserTag());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialDto.getPassword(), user.getPassword())) {
                return user;
            }
        }

        throw new AuthenticationException("User tag or password is not correct");
    }

    private User findByUserTag(String userTag) throws Exception {
        return userRepository.findUserByUserTag(userTag).orElseThrow(() -> new Exception(String.format("User with user tag %s not found", userTag)));
    }
}
