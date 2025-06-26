package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ElectionOption {

    @Valid
    @NotNull(message = "GiveElection must not be null")
    private GiveElection giveElection;

    @Valid
    @NotNull(message = "ReceiveElection must not be null")
    private ReceiveElection recieveElection;

    @Valid
    @NotNull(message = "Arbitrage must not be null")
    private Arbitrage arbitrage;

    @Valid
    @NotNull(message = "Balance must not be null")
    private Balance balance;
}