package com.ca.election.notification.com.example.service;


import com.ca.election.notification.model.Event;
import com.ca.election.notification.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepo repository;

    public Mono<Event> saveSampleEvent() {
        Event event = new Event();
        event.setEventId("EVT123456");
        event.setSedol("B1YW440");
        event.setStockName("ABC Corporation");

        Event.CorporateAction ca = new Event.CorporateAction();
        ca.setCaType("Dividend");
        ca.setCaDeadline(Instant.parse("2025-05-20T17:00:00Z"));
        ca.setDate1(Instant.parse("2025-05-18T00:00:00Z"));
        ca.setDate2(Instant.parse("2025-05-22T00:00:00Z"));

        event.setCorporateAction(ca);
        event.setCreatedAt(Instant.now());
        event.setUpdatedAt(Instant.now());

        return repository.save(event);
    }

    public Mono<Event> getEventBySedol(String sedolId) {
        return repository.findBySedol(sedolId);
    }


    public Mono<Event> getEventById(String id) {
        return repository.findById(id);
    }
}

