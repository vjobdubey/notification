package com.ca.election.notification.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class Configuration {

    @NotNull(message = "Deadline is required")
    private Instant deadline;
}
