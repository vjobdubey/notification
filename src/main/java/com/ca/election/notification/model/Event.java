package com.ca.election.notification.model;

import com.ca.election.notification.util.EmailStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "event")
@Getter
@Setter
public class Event {

    @Id
    private String eventId;  // Mapped to EventID (e.g., EVT123456)

    private String sedol;
    private String stockName;

    private CorporateAction corporateAction;

    private Instant createdAt;
    private Instant updatedAt;
    private EmailStatus emailStatus;

    // ---------------- Inner Class ----------------
    @Getter
    @Setter
    public static class CorporateAction {
        private String caType;
        private Instant caDeadline;
        private Instant date1; // Announcement Date
        private Instant date2; // Payment Date
    }



}

