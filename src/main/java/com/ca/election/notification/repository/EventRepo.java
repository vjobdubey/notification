package com.ca.election.notification.repository;

import com.ca.election.notification.model.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EventRepo extends ReactiveMongoRepository<Event, String> {
    Mono<Event> findBySedol(String sedol);
 }
