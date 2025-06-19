package com.ca.election.notification.scheduler;

import com.ca.election.notification.service.NotificationService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class NotificationScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);

    private final NotificationService notificationService;
    private Disposable currentTask;
    private final AtomicBoolean isShuttingDown = new AtomicBoolean(false);

    private final ZoneId zoneId = ZoneId.of("Asia/Kolkata");
    private final List<LocalTime> runTimes = Arrays.asList(
            LocalTime.of(2, 0),   // 2 AM
            LocalTime.of(10, 0),  // 10 AM
            LocalTime.of(18, 0)   // 6 PM
    );

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void startScheduler() {
        scheduleNextExecution();
    }

    private void scheduleNextExecution() {
        if (isShuttingDown.get()) {
            return;
        }

        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime nextRun = getNextRunTime(now);
        Duration delay = Duration.between(now, nextRun);

        log.info("Next notification processing scheduled at: {}", nextRun);

        currentTask = Mono.delay(delay)
                .flatMap(tick -> {
                    if (isShuttingDown.get()) return Mono.empty();
                    log.info("Starting notification process at: {}", ZonedDateTime.now(zoneId));
                    return notificationService.process()
                            .doOnNext(result -> log.info("NotificationService result: {}", result))
                            .doOnError(e -> log.error("Error in notification process: {}", e.getMessage(), e))
                            .onErrorResume(e -> Mono.empty());
                })
                .doOnTerminate(this::scheduleNextExecution)
                .subscribe();
    }

    private ZonedDateTime getNextRunTime(ZonedDateTime now) {
        for (LocalTime time : runTimes) {
            ZonedDateTime scheduledTime = now.with(time);
            if (scheduledTime.isAfter(now)) {
                return scheduledTime;
            }
        }
        return now.plusDays(1).with(runTimes.get(0));
    }

    @PreDestroy
    public void stopScheduler() {
        log.info("Stopping scheduler...");
        isShuttingDown.set(true);
        if (currentTask != null && !currentTask.isDisposed()) {
            currentTask.dispose();
        }
        log.info("Scheduler stopped.");
    }
}
