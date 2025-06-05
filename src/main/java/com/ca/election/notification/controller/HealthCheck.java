package com.ca.election.notification.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthCheck {
    @Value("${env}")
    String env;
    @GetMapping("/health-check")
    public Mono<String> healthCheck(){
        return Mono.just(env+":ok");

    }

}
