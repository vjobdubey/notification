package com.ca.election.notification.service;

import com.ca.election.notification.model.Event;
import com.ca.election.notification.repository.NotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public Flux<Event> process() {
        return notificationDao.findAndMarkOnePendingEmail();
    }




}
