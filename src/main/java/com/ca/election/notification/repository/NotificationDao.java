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
import java.time.Instant;
import java.util.Date;

@Repository
public class NotificationDao {
    private static final Logger log = LoggerFactory.getLogger(NotificationDao.class);
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;


    public Mono<Boolean> processAllPendingEmails() {
        Query query = new Query(Criteria.where("emailStatus").is(EmailStatus.PENDING)).limit(4);

        return mongoTemplate.find( query, Event.class )
                .flatMap( event -> {
                    // Step 1: Try to claim the event
                    Query updateQuery = new Query( Criteria.where( "_id" ).is( event.getEventId() )
                            .and( "emailStatus" ).is( EmailStatus.PENDING ) );
                    Update update = new Update()
                            .set( "emailStatus", EmailStatus.INITIATED.toString() )
                            .set( "claimedAt", Instant.now() );

                    return mongoTemplate.findAndModify( updateQuery, update, Event.class )
                            .flatMap( claimedEvent -> {
                                if (claimedEvent == null) {
                                    return Mono.empty().hasElement(); // Already claimed by another thread
                                }

                                // Step 2: Send email
                                return sendEmail( claimedEvent )
                                        .retryWhen( Retry.fixedDelay( 3, Duration.ofSeconds( 2 ) ) )
                                        .onErrorResume( e -> {
                                            log.error( "Error sending email for event {}: {}", claimedEvent.getEventId(), e.getMessage(), e );
                                            return updateEventStatus( claimedEvent, EmailStatus.FAILED ).then();
                                        } )
                                        .then( updateEventStatus( claimedEvent, EmailStatus.COMPLETED ) )
                                        .doOnSuccess( v -> {
                                            log.info( "Event marked COMPLETED: {}", claimedEvent.getEventId() );
                                        } ).thenReturn(true);
                            } );
                }, 2 ).hasElements();


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
