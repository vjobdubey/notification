package com.ca.election.notification.repository;

import com.ca.election.notification.model.Event;
import com.ca.election.notification.util.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class NotificationDao {
    private static final Logger log = LoggerFactory.getLogger(NotificationDao.class);
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;





    public Flux<Void> processAllPendingEmails(AtomicInteger  processedCount, AtomicBoolean stop) {
        return Flux.defer(() -> {

            return mongoTemplate.findAndModify(getPendingEvent(), updateToInitiated(), Event.class)
                    .flatMap(event -> {
                        processedCount.incrementAndGet(); // Track each processed event
                        log.info("Processing Event: {}", event.getEventId());

                        return sendEmail(event)
                                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                                .onErrorResume(e -> {
                                    log.error("Error sending email for event {}: {}", event.getEventId(), e.getMessage(), e);
                                    return Mono.empty();
                                })
                                .then(updateEventStatus(event, EmailStatus.COMPLETED))
                                .doOnSuccess(v -> log.info("Event marked COMPLETED: {}", event.getEventId()))
                                .onErrorResume(e -> {
                                    updateEventStatus(event, EmailStatus.FAILED);
                                    log.error("Failed to update status for event {}: {}", event.getEventId(), e.getMessage(), e);
                                    return Mono.empty();
                                });
                    })
                    .onErrorResume(e -> {
                        log.error("MongoTemplate error: {}", e.getMessage(), e);
                        return Mono.delay(Duration.ofSeconds(2)).then(Mono.empty());
                    }).repeat(10);

        });
    }

    private static Query getPendingEvent() {
        return new Query(Criteria.where("emailStatus").is(EmailStatus.PENDING)).limit(1);
    }

    private Update updateToInitiated() {
        return new Update().set("emailStatus", EmailStatus.INITIATED).set("updatedAt", new Date());
    }


    private Mono<Void> updateEventStatus(Event event, EmailStatus Status) {
        log.info("Event completed:{}", event.getEventId());
        Query query = Query.query(Criteria.where("_id").is(event.getEventId()));
        Update update = new Update().set("emailStatus", Status).set("updatedAt", new Date());
        return mongoTemplate.updateFirst(query, update, Event.class).then();// return Mono<Void>
    }


    private Mono<Void> sendEmail(Event event) {
        log.info("Sending email to: {}", event.getEventId());
        // Simulate async mail send
        return Mono.fromRunnable(() -> {
            // email logic here (non-blocking if real)
            log.info("Email sent to: {}", event);
        });
    }


}
