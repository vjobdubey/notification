package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Document(collection = "caEvent")
@Data
public class Event {

    @Id
    @NotBlank(message = "Event ID is required")
    @Field("_id")
    private String eventId;

    @NotNull(message = "CreatedAt is required")
    private Instant createdAt;

    @NotNull(message = "UpdatedAt is required")
    private Instant updatedAt;

    @NotBlank(message = "Email status is required")
    private String emailStatus;

    private Instant claimedAt;

    @Valid
    @NotNull(message = "Positions are required")
    private List<Position> positions;

    @Valid
    @NotNull(message = "Transaction is required")
    private Transaction transaction;

    @Valid
    @NotNull(message = "Election management is required")
    private ElectionManagement electionManagement;
}