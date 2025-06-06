package com.ca.election.notification.repository;

import com.ca.election.notification.model.Event;
import com.ca.election.notification.util.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public class NotificationDao {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public void findAndMarkOnePendingEmail() {
        System.out.println("processing events");

        Query query = new Query(Criteria.where("emailStatus").is(EmailStatus.PENDING))
                //.with(Sort.by(Sort.Direction.ASC, "createdAt"))
                .limit(1);

        Update update = new Update()
                .set("emailStatus", EmailStatus.INITIATED)
                .set("updatedAt", new Date());

        mongoTemplate.findAndModify(query, update, Event.class)
                .onErrorResume(e -> {
                    System.err.println("Handling MongoTemplate error gracefully: " + e.getMessage());
                    return Mono.empty();
                })
                .doOnNext(event -> {
                    System.out.println("Found and updated one PENDING email.");
                    findAndMarkOnePendingEmail(); // Safe if returns void
                })
                .switchIfEmpty(Mono.fromRunnable(() -> System.out.println("No PENDING email found.")))
                .subscribe(); // Starts the chain
    }



}
