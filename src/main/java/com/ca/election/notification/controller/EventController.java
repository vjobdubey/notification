package com.ca.election.notification.controller;

import com.ca.election.notification.com.example.service.EventService;
import com.ca.election.notification.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/event/{id}")
    public Mono<Event> getEvent(@PathVariable("id") String id){
        return eventService.getEventById(id);
    }
}
