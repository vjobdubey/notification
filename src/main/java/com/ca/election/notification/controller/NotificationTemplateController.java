package com.ca.election.notification.controller;

import com.ca.election.notification.model.DropdownOption;
import com.ca.election.notification.model.NotificationTemplateDropdownResponse;
import com.ca.election.notification.util.CorporateActionType;
import com.ca.election.notification.util.ElectionType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notification/template")
public class NotificationTemplateController {

    @GetMapping("/dropdown-options")
    @Cacheable("template-dropdowns")
    public Mono<ResponseEntity<NotificationTemplateDropdownResponse>> getTemplateDropdownOptions() {
        NotificationTemplateDropdownResponse response = new NotificationTemplateDropdownResponse();
        
        response.setCorporateActionTypes(Arrays.stream(CorporateActionType.values())
                .map(type -> new DropdownOption(type.getValue(), type.getLabel()))
                .collect(Collectors.toList()));
        
        response.setElectionTypes(Arrays.stream(ElectionType.values())
                .map(type -> new DropdownOption(type.getValue(), type.getLabel()))
                .collect(Collectors.toList()));
        
        response.setElectionForms(null);
        
        return Mono.just(ResponseEntity.ok(response));
    }

    @GetMapping("/election-forms")
    public Mono<ResponseEntity<List<DropdownOption>>> getElectionForms(
            @RequestParam String corporateActionType,
            @RequestParam String electionType) {
        
        return callExternalServiceForElectionForms(corporateActionType, electionType)
                .map(ResponseEntity::ok);
    }

    private Mono<List<DropdownOption>> callExternalServiceForElectionForms(String corporateActionType, String electionType) {
        return Mono.empty();
    }
} 