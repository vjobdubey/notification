package com.ca.election.notification.service;
import com.ca.election.notification.repository.NotificationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationDao notificationDao;

    public Mono<String> process() {
        return notificationDao.processAllPendingEmails()
                .repeat()  // repeat after delay
                .takeUntil(result -> !result)// stop repeating when result is false
                .then(Mono.just("Notification processed...{}"));
    }

}
