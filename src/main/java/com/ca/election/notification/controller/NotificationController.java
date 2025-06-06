package com.ca.election.notification.controller;

import com.ca.election.notification.model.Event;
import com.ca.election.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("notification/process")
    public Mono<String> process() {
            System.out.println("notification");
         notificationService.process();
        return Mono.just("notification processed..");
    }


}
