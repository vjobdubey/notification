package com.ca.election.notification.security;

import com.ca.election.notification.config.JwtConfig;
import com.ca.election.notification.exception.CAException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements ServerSecurityContextRepository {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String HEALTH_CHECK_PATH = "/health-check";
    private static final String GET_TOKEN_PATH = "/test/token";

    private final JwtConfig jwtConfig;

    public JwtAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().toString();
        
        if (HEALTH_CHECK_PATH.equals(path) || GET_TOKEN_PATH.equals(path)) {
            return Mono.empty();
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        
        if (authHeader == null) {
            return Mono.error(new CAException("Authorization header is missing"));
        }

        try {
            String token = JwtConfig.extractToken(authHeader);
            
            Claims claims = jwtConfig.validateToken(token);
            
            String subject = claims.getSubject();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                subject, null, null);
            
            return Mono.just(new SecurityContextImpl(auth));
        } catch (IllegalArgumentException e) {
            return Mono.error(new CAException("Invalid Authorization header format"));
        } catch (Exception e) {
            return Mono.error(new CAException("Invalid JWT token: " + e.getMessage()));
        }
    }
} 