package ru.lcp.axol.axolapi.auth.config.filters;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.lcp.axol.axolapi.auth.entity.CustomUserDetails;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration lifetime;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Authentication auth) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        UUID userId = user.getId();

        Date now = new Date();
        Date exp = new Date(now.getTime() + lifetime.toMillis());

        List<String> roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("userId", userId.toString())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getClaimAsString(String token, String claimName) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, String.class);
    }
}