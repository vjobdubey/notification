package com.ca.election.notification.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtConfig {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final long MIN_EXPIRATION_TIME = 60;
    private static final long MAX_EXPIRATION_TIME = 1440;
    private static final String ERROR_MESSAGE = "You don't have permission to access this resource";

    @Value("${jwt.access-token.expiration}")
    private long expirationTime;

    @Value("${jwt.private.key}")
    private String privateKey;

    public String getPrivateKey() {
        if (privateKey == null || privateKey.trim().isEmpty()) {
            throw new IllegalStateException("Internal server configuration issue");
        }
        return privateKey;
    }

    public long getExpirationTime() {
        if (expirationTime < MIN_EXPIRATION_TIME || expirationTime > MAX_EXPIRATION_TIME) {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
        return expirationTime * 60 * 1000; 
    }

    public static String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            throw new IllegalArgumentException();
        }
        return authHeader.substring(TOKEN_PREFIX.length());
    }

    public Claims validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(getPrivateKey().getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new IllegalArgumentException(ERROR_MESSAGE);
            }

            return claims;
        } catch (Exception e) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
} 