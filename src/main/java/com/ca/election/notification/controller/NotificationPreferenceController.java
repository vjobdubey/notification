package com.ca.election.notification.controller;

import com.ca.election.notification.model.NotificationPreference;
import com.ca.election.notification.service.NotificationPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notification-preference")
public class NotificationPreferenceController {
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<NotificationPreference> savePreference(@RequestBody NotificationPreference preference) {
        return notificationPreferenceService.savePreference(preference);
    }

    // if no preference added, then there would be some default message
    @GetMapping(value = "/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<NotificationPreference> getPreference(@PathVariable String clientId) {
        return notificationPreferenceService.getPreference(clientId);
    }
} 