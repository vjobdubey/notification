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

    public Flux<Event> findAndMarkOnePendingEmail() {
        Query query = new Query(Criteria.where("emailStatus").is(EmailStatus.INITIATED))
                //.with(Sort.by(Sort.Direction.ASC, "createdAt"))
                .limit(1);

        Update update = new Update()
                .set("emailStatus", EmailStatus.COMPLETED)
                .set("updatedAt", new Date());

        mongoTemplate.findAndModify(query, update, Event.class)
                .doOnError(e -> {
                    System.err.println("MongoTemplate do error: " + e.getClass().getName() + " - " + e.getMessage());
                    e.printStackTrace();
                })
                .onErrorResume(e -> {
                    System.err.println("MongoTemplate resume error: " + e.getClass().getName() + " - " + e.getMessage());
                    e.printStackTrace();
                    return Mono.empty();
                })
                .subscribe(); // 🔴 Required to trigger the pipeline

        return Flux.just(new Event());

    }



}
