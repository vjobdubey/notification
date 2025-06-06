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
        Query query = new Query(Criteria.where("emailStatus").is(EmailStatus.PENDING))
                //.with(Sort.by(Sort.Direction.ASC, "createdAt"))
                .limit(1);

        Update update = new Update()
                .set("emailStatus", EmailStatus.INITIATED)
                .set("updatedAt", new Date());

        mongoTemplate.findAndModify(query, update, Event.class)
                .doOnError(e -> {
                    System.err.println("MongoTemplate error: " + e.getClass().getName() + " - " + e.getMessage());
                    e.printStackTrace();
                })
                .onErrorResume(e -> {
                    System.err.println("Handling MongoTemplate error gracefully.");
                    return Mono.empty();
                })
                .flatMap(event -> {
                    if (event != null) {
                        System.out.println("Found and updated one PENDING email.");
                        // Recursive call in flatMap is safe in reactive
                        findAndMarkOnePendingEmail();
                    } else {
                        System.out.println("No PENDING email found.");
                    }
                    return Mono.empty();
                })
                .subscribe();
    }



}
