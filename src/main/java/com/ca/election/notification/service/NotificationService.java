package com.ca.election.notification.service;

import com.ca.election.notification.model.Event;
import com.ca.election.notification.repository.NotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public Mono<String> process() {
        AtomicInteger  processedCount = new AtomicInteger(0);
        AtomicBoolean stop = new AtomicBoolean(false);

        notificationDao.processAllPendingEmails(processedCount, stop).subscribe();
        return Mono.just(" Notification processed..");
    }




}
