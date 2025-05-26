package com.ca.election.notification.repository;

import com.ca.election.notification.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepo extends MongoRepository<Event, Integer> {
}
