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
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class NotificationDao {
    private static final Logger log = LoggerFactory.getLogger(NotificationDao.class);
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public void findAndMarkOnePendingEmail(AtomicInteger counter) {
        mongoTemplate.findAndModify(getPendingEvent(), updateToInitiated(), Event.class)
                .onErrorResume(e -> {
                    log.error("MongoTemplate error: {}", e.getMessage(), e);
                    return Mono.empty();
                })
                .flatMap(event -> {
                    if (event == null) {
                        return Mono.empty();
                    }

                    counter.incrementAndGet();
                    log.info("Processing Event: {}", event);

                    return sendEmail(event)
                            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                            .then(updateStatusToCompleted(event))
                            .doOnSuccess(v -> log.info("Event marked COMPLETED: {}", event.getEventId()))
                            .then(Mono.defer(() -> {
                                log.info("Continuing to next email...");
                                return Mono.fromRunnable(() -> findAndMarkOnePendingEmail(counter));
                            }));
                })
                .switchIfEmpty(Mono.fromRunnable(() ->
                        log.info("No pending emails. Total processed: {}", counter.get())
                ))
                .subscribe(
                        null,
                        e -> log.error("Unexpected error in stream: {}", e.getMessage(), e),
                        () -> log.debug("Reactive stream completed")
                );
    }

    private static Query getPendingEvent() {
        return new Query(Criteria.where("emailStatus").is(EmailStatus.PENDING)).limit(1);
    }

    private Update updateToInitiated() {
        return new Update().set("emailStatus", EmailStatus.INITIATED).set("updatedAt", new Date());
    }


    private Mono<Void> updateStatusToCompleted(Event event) {
        log.info("Event completed:{}", event);
        Query query = Query.query(Criteria.where("_id").is(event.getEventId()));
        Update update = new Update().set("emailStatus", EmailStatus.COMPLETED).set("updatedAt", new Date());
        return mongoTemplate.updateFirst(query, update, Event.class).then();// return Mono<Void>
    }


    private Mono<Void> sendEmail(Event event) {
        log.info("Sending email to: {}", event);
        // Simulate async mail send
        return Mono.fromRunnable(() -> {
            // email logic here (non-blocking if real)
            log.info("Email sent to: {}", event);
        });
    }


}
