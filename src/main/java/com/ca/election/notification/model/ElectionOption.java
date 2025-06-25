//package com.ca.election.notification.model;
//
//import jakarta.validation.Valid;
//import lombok.Data;
//
//@Data
//public class ElectionOption {
//
//    @Valid
//    private GiveElection giveElection;
//
//    @Valid
//    private ReceiveElection receiveElection;
//
//    @Valid
//    private Arbitrage arbitrage;
//
//    @Valid
//    private Balance balance;
//
//    @Data
//    public static class GiveElection {
//        private int totalQuantity;
//        private int electionQuantity;
//    }
//
//    @Data
//    public static class ReceiveElection {
//        private String details;
//    }
//
//    @Data
//    public static class Arbitrage {
//        private String strategy;
//    }
//
//    @Data
//    public static class Balance {
//        private int units;
//    }
//}

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
    private ReceiveElection receiveElection;

    @Valid
    @NotNull(message = "Arbitrage must not be null")
    private Arbitrage arbitrage;

    @Valid
    @NotNull(message = "Balance must not be null")
    private Balance balance;
}