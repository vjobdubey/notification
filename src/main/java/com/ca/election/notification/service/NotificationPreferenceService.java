package com.ca.election.notification.service;

import com.ca.election.notification.model.NotificationPreference;
import com.ca.election.notification.repository.NotificationPreferenceDao;
import com.ca.election.notification.util.NotificationPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class NotificationPreferenceService {
    @Autowired
    private NotificationPreferenceDao notificationPreferenceDao;

    public Mono<NotificationPreference> savePreference(NotificationPreference preference) {
        return notificationPreferenceDao.save(preference);
    }

    public Mono<NotificationPreference> getPreference(String userEmail) {
        return notificationPreferenceDao.findById(userEmail)
            .switchIfEmpty(Mono.fromSupplier(() -> {
                NotificationPreference defaultPref = new NotificationPreference();
                defaultPref.setEmailId(userEmail);
                defaultPref.setNotificationPreferences(Collections.singletonList(NotificationPreferences.EMAIL));
                return defaultPref;
            }));
    }
} 