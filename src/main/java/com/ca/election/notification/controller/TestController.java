package com.ca.election.notification.controller;

import com.ca.election.notification.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "test");
        String token = jwtTokenProvider.generateToken("test-user", claims);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
} 