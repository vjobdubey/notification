package com.ca.election.notification.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Calypso {

    @NotBlank(message = "Trade ID is required")
    private String tradeId;

    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Currency is required")
    private String currency;
}
