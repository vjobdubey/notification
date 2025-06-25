package com.ca.election.notification.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "client")
@Data
public class Client {

    @Id
    @NotBlank(message = "Client _id is required")
    private String id;

    @NotBlank(message = "Client ID is required")
    private String clientId;

    @Email(message = "Email ID must be a valid email address")
    @NotBlank(message = "Email ID is required")
    private String emailId;

    @NotNull(message = "Preferences must not be null")
    private List<String> preferences;

    @NotBlank(message = "Locale is required")
    private String locale;

    @NotBlank(message = "Client type is required")
    private String type;
}