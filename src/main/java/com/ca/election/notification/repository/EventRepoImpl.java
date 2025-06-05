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
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Date;

public abstract class EventRepoImpl implements EventRepo {
        @Autowired
        private ReactiveMongoTemplate mongoTemplate;

        public Mono<Event> findAndMarkOnePendingEmail() {
            Query query = new Query(Criteria.where("emailStatus").is(EmailStatus.PENDING))
                    .with(Sort.by(Sort.Direction.ASC, "createdAt"))
                    .limit(3);

            Update update = new Update()
                    .set("emailStatus", EmailStatus.INITIATED)
                    .set("updatedAt", new Date());

            return mongoTemplate.findAndModify(query, update,
                    FindAndModifyOptions.options().returnNew(true),
                    Event.class);
        }


    }
