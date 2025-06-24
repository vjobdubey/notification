package com.ca.election.notification.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Document(collection = "caEvent")
@Data
public class Event {

    @Id
    @NotBlank(message = "Event ID is required")
    private String eventId;

    @PastOrPresent(message = "Created time must be in the past or present")
    private Instant createdAt;

    @PastOrPresent(message = "Updated time must be in the past or present")
    private Instant updatedAt;

    @NotBlank(message = "Email status is required")
    private String emailStatus;

    @PastOrPresent(message = "Claimed time must be in the past or present")
    private Instant claimedAt;

    @NotEmpty(message = "At least one position must be provided")
    @Valid
    private List<Position> positions;

    @NotNull(message = "Transactions object is required")
    @Valid
    private Transactions transactions;

    @NotNull(message = "Election management data is required")
    @Valid
    private ElectionManagement electionManagement;

    @Data
    public static class Position {
        @NotNull(message = "Risk information must be provided")
        @Valid
        private Risk risk;

        @NotNull(message = "Holder information must be provided")
        @Valid
        private Holder holder;

        private boolean settled;

        @Valid
        private SBL sbl;

        @Valid
        private SBLThirdParty sblThirdParty;

        @Valid
        private NonStandard nonstandard;

        @Valid
        private Synthetic synthetic;
    }

    @Data
    public static class Risk {
        @NotBlank(message = "Risk ID is required")
        private String id;

        @NotBlank(message = "Risk type is required")
        private String type;

        @NotNull(message = "Risk date must be provided")
        private Instant date;

        @NotBlank(message = "Source system is required")
        private String sourceSystem;

        @NotBlank(message = "Source system ID is required")
        private String sourceSystemId;

        @NotNull(message = "Instrument details must be provided")
        @Valid
        private Instrument instrument;
    }

    @Data
    public static class Instrument {
        @NotBlank(message = "Sedol is required")
        private String sedol;

        @NotBlank(message = "RIC is required")
        private String ric;

        @NotBlank(message = "ISIN is required")
        private String isin;

        @NotBlank(message = "Instrument name is required")
        private String name;

        @NotBlank(message = "Instrument type is required")
        private String type;
    }

    @Data
    public static class Holder {
        @NotBlank(message = "Holder ID is required")
        private String id;

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

        private boolean isBackToBack;
    }

    @Data
    public static class SBL {
        private boolean active;
    }

    @Data
    public static class SBLThirdParty {
        private String provider;
        private String status;
    }

    @Data
    public static class NonStandard {
        private boolean override;
    }

    @Data
    public static class Synthetic {
        private boolean flag;
    }

    @Data
    public static class Transactions {
        @NotNull(message = "Calypso trade details are required")
        @Valid
        private Trade calypso;

        @NotNull(message = "Quantum trade details are required")
        @Valid
        private Trade quantum;
    }

    @Data
    public static class Trade {
        private String tradeId;
        private String bookingId;
        private double amount;
        private String currency;
    }

    @Data
    public static class ElectionManagement {
        @NotBlank(message = "Option change details are required")
        private String optionChangeDetails;

        @NotBlank(message = "Election option status is required")
        private String electionOptionStatus;

        @NotBlank(message = "Election updated by must be provided")
        private String electionUpdatedBy;

        @NotNull(message = "Election block is required")
        @Valid
        private Election election;

        @NotNull(message = "Price details are required")
        private Map<String, Double> prices;

        @NotNull(message = "Configuration is required")
        @Valid
        private Configuration configuration;

        @NotNull(message = "Options section is required")
        @Valid
        private Options options;

        @NotNull(message = "Custody deadline is required")
        private Instant custodyDeadline;
    }

    @Data
    public static class Election {
        @Valid
        private ElectionOption CITILO;

        @Valid
        private ElectionOption SYNTH;
    }

    @Data
    public static class ElectionOption {
        @Valid
        private GiveElection giveElection;

        @Valid
        private ReceiveElection recieveElection;

        @Valid
        private Arbitrage arbitrage;

        @Valid
        private Balance balance;

        private String strategy;
    }

    @Data
    public static class GiveElection {
        @Valid
        private List<ElectionItem> items;

        private int totalQuantity;

        private int ElectionQuantity;
    }

    @Data
    public static class ElectionItem {
        private String type;
        private int quantity;
    }

    @Data
    public static class ReceiveElection {
        private String details;
    }

    @Data
    public static class Arbitrage {
        private String strategy;
    }

    @Data
    public static class Balance {
        private int amount;
    }

    @Data
    public static class Configuration {
        @NotNull(message = "Configuration deadline is required")
        private Instant deadline;
    }

    @Data
    public static class Options {
        @NotEmpty(message = "At least one available option is required")
        private List<String> availableOptions;
    }
}