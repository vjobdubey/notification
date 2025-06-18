package com.ca.election.notification.model;

import com.ca.election.notification.util.NotificationPreferences;
import com.ca.election.notification.util.UserType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "notification_preference")
public class NotificationPreference {
    @Id
    private String clientId;
    private String emailId;
    private List<NotificationPreferences> notificationPreferences;
    private String locale;
    private UserType userType;
} 