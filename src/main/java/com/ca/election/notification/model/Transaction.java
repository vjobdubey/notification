package com.ca.election.notification.model;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class Transaction {

    @Valid
    private Calypso calypso;

    @Valid
    private Quantum quantum;
}
