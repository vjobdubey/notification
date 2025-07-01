package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Position {

    @Valid
    @NotNull(message = "Risk is required")
    private Risk risk;

    @Valid
    @NotNull(message = "Holder is required")
    private Holder holder;

    private boolean settled;
    private SBL sbl;
    private SBLThirdParty sblThirdParty;
    private NonStandard nonstandard;
    private Synthetic synthetic;
}