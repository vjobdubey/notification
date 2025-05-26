package com.ca.election.notification.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

// Annotations
@Data
@Document(collection = "Event")
public class Event {


    @Id
    private int eventId;
    private String sedol;
    private String stockName;
    private String caType;
    private String  caDeadline;
    private LocalDate Date1;
    private LocalDate Date2;

}
