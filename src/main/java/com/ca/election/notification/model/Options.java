package com.ca.election.notification.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class Options {

    @NotEmpty(message = "Available options must not be empty")
    private List<String> availableOptions;
}

