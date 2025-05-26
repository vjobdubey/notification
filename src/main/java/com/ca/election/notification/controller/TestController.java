package com.ca.election.notification.controller;

import com.ca.election.notification.model.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @GetMapping("/test")
    public Mono<String> test(){

        return Mono.just("test");

    }

}
