package com.ca.election.notification.service;

import com.ca.election.notification.model.Event;
import com.ca.election.notification.repository.NotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public Mono<String> process() {
        AtomicInteger counter = new AtomicInteger(0);
        notificationDao.findAndMarkOnePendingEmail(counter);
        return Mono.just(" Notification processed..");
    }




}
