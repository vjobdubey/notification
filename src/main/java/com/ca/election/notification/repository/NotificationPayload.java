package com.ca.election.notification.repository;


import com.ca.election.notification.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPayload {
    private String eventId;
    private Instant createdAt;
    private Instant updatedAt;
    private String emailStatus;
    private List<Position> positions;
    private List<String> preferences;
    private String emailId;
}

