package ru.axol.authservice.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.axol.authservice.dto.JwtAuthenticationDto;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessExpirationInMinutes}")
    private int accessExpirationInMinutes;

    @Value("${jwt.refreshExpirationInDays}")
    private int refreshExpirationInDays;

    public JwtAuthenticationDto generateAuthenticationToken(String userTag) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setAccessToken(generateAccessToken(userTag));
        jwtAuthenticationDto.setRefreshToken(generateRefreshToken(userTag));
        return jwtAuthenticationDto;
    }

    public JwtAuthenticationDto refreshAccessToken(String userTag, String refreshToken) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setAccessToken(generateAccessToken(userTag));
        jwtAuthenticationDto.setRefreshToken(refreshToken);
        return jwtAuthenticationDto;
    }

    public String getUserTag(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: ", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: ", e);
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token: ", e);
        } catch (SecurityException e) {
            log.error("Security exception: ", e);
        } catch (Exception e) {
            log.error("Invalid token: ", e);
        }
        return false;
    }

    private String generateAccessToken(String userTag) {
        Date date = Date
                .from(LocalDateTime
                        .now()
                        .plusMinutes(accessExpirationInMinutes)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                );
        return Jwts.builder()
                .subject(userTag)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private String generateRefreshToken(String userTag) {
        Date date = Date
                .from(LocalDateTime
                        .now()
                        .plusDays(refreshExpirationInDays)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                );
        return Jwts.builder()
                .subject(userTag)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
