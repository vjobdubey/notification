package com.ca.election.notification.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReceiveElection {

    @NotBlank(message = "Details must not be blank")
    private String details;
}