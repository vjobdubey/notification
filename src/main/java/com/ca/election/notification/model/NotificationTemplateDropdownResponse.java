package com.ca.election.notification.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationTemplateDropdownResponse {
    private List<DropdownOption> corporateActionTypes;
    private List<DropdownOption> electionTypes;
    private List<DropdownOption> electionForms;

    public NotificationTemplateDropdownResponse() {
        this.corporateActionTypes = new ArrayList<>();
        this.electionTypes = new ArrayList<>();
        this.electionForms = null;
    }
} 