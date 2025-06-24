package com.ca.election.notification.scheduler;

import com.ca.election.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;

@Component
public class NotificationScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);

    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "${notification.scheduler.cron}", zone = "${notification.scheduler.zone}")
    public void runScheduledJob() {
        log.info("Triggering job at {}", ZonedDateTime.now());

        notificationService.process()
                .doOnNext(result -> log.info("Result: {}", result))
                .doOnError(err -> log.error("Error in scheduled task", err))
                .doOnSuccess(done -> log.info("Notification job completed"))
                .subscribe();
    }
}
