package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class ElectionManagement {

    @NotBlank(message = "Option change details is required")
    private String optionChangeDetails;

    @NotBlank(message = "Election option status is required")
    private String electionOptionStatus;

    @NotBlank(message = "Election updated by is required")
    private String electionUpdatedBy;

    @Valid
    @NotNull(message = "Election is required")
    private Election election;

    private Map<String, Double> prices;

    @Valid
    @NotNull(message = "Configuration is required")
    private Configuration configuration;

    @Valid
    @NotNull(message = "Options are required")
    private Options options;

    @NotNull(message = "Custody deadline is required")
    private Instant custodyDeadline;
}