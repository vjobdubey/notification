package com.ca.election.notification.util;

import lombok.Getter;

@Getter
public enum NotificationPreferences {
    EMAIL("Email Address", "EMAIL"),
    TEAMS("Microsoft Teams", "TEAMS"),
    CALENDAR("Calendar", "CALENDAR");

    private final String value;
    private final String label;

    NotificationPreferences(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
