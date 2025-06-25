package com.ca.election.notification.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Arbitrage {

    @NotBlank(message = "Strategy must not be blank")
    private String strategy;
}
