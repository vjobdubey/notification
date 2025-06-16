package com.ca.election.notification.controller;

import com.ca.election.notification.service.EventService;
import com.ca.election.notification.model.Event;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@Validated
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/event/{id}")
    public Mono<Event> getEvent(@PathVariable("id") String id)  {
        return eventService.getEventById(id);
    }

    @GetMapping("/event/allEvents")
    public Flux<Event> getAllEvents() {
        return eventService.getEventList();
    }

    //to be deleted later
    @PostMapping("/event/create")
    public Mono<Event> create(@Valid @RequestBody Event event) {
        return eventService.create(event);
    }

    @PutMapping("/event/update/{id}")
    public Mono<Event> update(@PathVariable String id, @Valid @RequestBody Event event) {
        return eventService.update(id, event);
    }

    @DeleteMapping("/event/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return eventService.delete(id);
    }
}
