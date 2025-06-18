package com.ca.election.notification.repository;

import com.ca.election.notification.model.NotificationPreference;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NotificationPreferenceDao extends ReactiveMongoRepository<NotificationPreference, String> {
} 