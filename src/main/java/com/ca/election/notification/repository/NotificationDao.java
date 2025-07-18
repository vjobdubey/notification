package com.ca.election.notification.repository;

import com.ca.election.notification.model.Client;
import com.ca.election.notification.model.Event;
import com.ca.election.notification.util.EmailStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Repository
public class NotificationDao {
    private static final Logger log = LoggerFactory.getLogger(NotificationDao.class);
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private SqsClient sqsClient;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


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
                                return sendEventToSQS( claimedEvent )
                                        .retryWhen( Retry.fixedDelay( 3, Duration.ofSeconds( 2 ) ) )
                                        .onErrorResume( e -> {
                                            log.error( "Error sending to SQS for event {}: {}", claimedEvent.getEventId(), e.getMessage(), e );
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

    private Mono<Void> sendEventToSQS(Event event) {
        // Extract clientCode from event's positions
        if (event.getPositions() == null || event.getPositions().isEmpty()) {
            log.warn("Event {} has no positions", event.getEventId());
            return Mono.empty();
        }

        Query clientQuery = new Query(Criteria.where("clientId").is(event.getPositions().get(0).getHolder().getClientCode().trim()));

        return mongoTemplate.findOne(clientQuery, Client.class)
                .flatMap(client -> {
                    return Mono.fromCallable(() -> {
                        try {
                            NotificationPayload payload = new NotificationPayload();
                            payload.setEventId(event.getEventId());
                            payload.setCreatedAt(event.getCreatedAt());
                            payload.setUpdatedAt(event.getUpdatedAt());
                            payload.setEmailStatus(event.getEmailStatus());
                            payload.setPositions(event.getPositions());
                            payload.setPreferences(client.getPreferences());
                            payload.setEmailId(client.getEmailId());

                            String messageBody = objectMapper.writeValueAsString(payload);

                            SendMessageRequest request = SendMessageRequest.builder()
                                    .queueUrl("https://sqs.eu-north-1.amazonaws.com/974389254636/ca-engine-notification-queue.fifo")
                                    .messageBody(messageBody)
                                    .messageGroupId("notification-group")
                                    .messageDeduplicationId(event.getEventId()) // required for FIFO
                                    .build();

                            sqsClient.sendMessage(request);
                            log.info("Sent event {} to SQS", event.getEventId());
                            return true;
                        } catch (Exception e) {
                            log.error("Error sending to SQS for event {}: {}", event.getEventId(), e.getMessage(), e);
                            throw new RuntimeException(e); // will be wrapped by Mono
                        }
                    });
                })
                .then();
    }


}