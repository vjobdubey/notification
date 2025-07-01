package com.ca.election.notification.repository;

import com.ca.election.notification.model.Event;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EventDao extends ReactiveMongoRepository<Event, String> {
    @Query("{ 'positions.instrument.sedol': ?0 }")
    Mono<Event> findBySedol(String sedol);
}
