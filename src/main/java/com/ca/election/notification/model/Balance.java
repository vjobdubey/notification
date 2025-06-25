package com.ca.election.notification.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Balance {

    @NotNull(message = "Amount/Units must not be null")
    @Min(value = 0, message = "Amount/Units must be 0 or more")
    private Integer amount;
}
