package com.ca.election.notification.util;

import lombok.Getter;

@Getter
public enum ElectionType {
    BINARY("binary", "Binary"),
    NON_BINARY("non_binary", "Non Binary");

    private final String value;
    private final String label;

    ElectionType(String value, String label) {
        this.value = value;
        this.label = label;
    }

}