package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class GiveElection {

    @NotNull(message = "Total quantity is required")
    private Integer totalQuantity;

    @NotNull(message = "Election quantity is required")
    private Integer electionQuantity;

    @NotEmpty(message = "Items must not be empty")
    @Valid
    private List<ElectionItem> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ElectionItem {

        @NotBlank(message = "Type is required")
        private String type;

        @NotNull(message = "Quantity is required")
        private Integer quantity;
    }
}
