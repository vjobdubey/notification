package com.ca.election.notification.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Instrument {

    @NotBlank(message = "Sedol is required")
    private String sedol;

    @NotBlank(message = "RIC is required")
    private String ric;

    @NotBlank(message = "ISIN is required")
    private String isin;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;
}