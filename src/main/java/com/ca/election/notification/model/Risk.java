package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
public class Risk {

    @NotBlank(message = "Risk ID is required")
    private String id;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Date is required")
    private Instant date;

    @NotBlank(message = "Source system is required")
    private String sourceSystem;

    @NotBlank(message = "Source system ID is required")
    private String sourceSystemId;

    @Valid
    @NotNull(message = "Instrument is required")
    private Instrument instrument;
}