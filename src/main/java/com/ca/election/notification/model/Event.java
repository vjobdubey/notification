package com.ca.election.notification.model;

import com.ca.election.notification.util.EmailStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "event")
@Data
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
    public static class CorporateAction {
        private String caType;
        private Instant caDeadline;
        private Instant date1; // Announcement Date
        private Instant date2; // Payment Date

        public String getCaType() {
            return caType;
        }

        public void setCaType(String caType) {
            this.caType = caType;
        }

        public Instant getCaDeadline() {
            return caDeadline;
        }

        public void setCaDeadline(Instant caDeadline) {
            this.caDeadline = caDeadline;
        }

        public Instant getDate1() {
            return date1;
        }

        public void setDate1(Instant date1) {
            this.date1 = date1;
        }

        public Instant getDate2() {
            return date2;
        }

        public void setDate2(Instant date2) {
            this.date2 = date2;
        }
    }



}

