package com.ca.election.notification.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Holder {

    @NotBlank(message = "Holder ID is required")
    private String hid;

    @NotBlank(message = "Legal entity is required")
    private String legalEntity;

    @NotBlank(message = "Group is required")
    private String group;

    @NotBlank(message = "Activity is required")
    private String activity;

    @NotBlank(message = "Account is required")
    private String account;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Site is required")
    private String site;

    @NotBlank(message = "Client code is required")
    private String clientCode;

    @NotBlank(message = "Client type is required")
    private String clientType;

    @NotNull(message = "isBackToBack is required")
    private Boolean isBackToBack;
}