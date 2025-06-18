package com.ca.election.notification.util;

import lombok.Getter;

@Getter
public enum CorporateActionType {
    BUYBACK("buyback", "Buyback"),
    STOCK_SPLIT("stock_split", "Stock Split"),
    DIVIDEND("dividend", "Dividend");

    private final String value;
    private final String label;

    CorporateActionType(String value, String label) {
        this.value = value;
        this.label = label;
    }

}