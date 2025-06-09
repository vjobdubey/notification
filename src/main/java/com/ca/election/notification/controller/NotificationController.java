package com.ca.election.notification.controller;

import com.ca.election.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("notification/process")
    public Mono<String> process() {
        return notificationService.process();
    }
    public Mono<String> process(@AuthenticationPrincipal String subject) {
        String userId = subject != null ? subject : "anonymous";
        return notificationService.process().map(event -> {
            return event + ": AuthUser Check -> " + userId;
        });
    }
}
