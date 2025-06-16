package com.ca.election.notification.model;

import com.ca.election.notification.util.EmailStatus;
import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "event")
@Data
public class Event {

    @Id
    @NotBlank(message = "Event ID is required")
    private String eventId;  // Mapped to EventID (e.g., EVT123456)
    @NotBlank(message = "Sedol cannot be blank")
    private String sedol;
    @NotBlank(message = "Stock name cannot be blank")
    private String stockName;

    @Valid  // Ensures nested object validation
    @NotNull(message = "Corporate Action details must be provided")
    private CorporateAction corporateAction;

    @Past(message = "Created date must be in the past")
    private Instant createdAt;
    @FutureOrPresent(message = "Updated date cannot be in the past")
    private Instant updatedAt;
    private EmailStatus emailStatus;

    // ---------------- Inner Class ----------------
    public static class CorporateAction {
        @NotBlank(message = "Corporate action type must not be blank")
        private String caType;
        @Future(message = "CA deadline must be in the future")
        private Instant caDeadline;
        @Past(message = "Announcement date (date1) must be in the past")
        private Instant date1; // Announcement Date
        @FutureOrPresent(message = "Payment date (date2) cannot be in the past")
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

