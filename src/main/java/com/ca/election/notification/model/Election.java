package com.ca.election.notification.model;

import lombok.Data;

import java.util.Map;

@Data
public class Election {
    private Map<String, ElectionOption> CITILO;
    private Map<String, ElectionOption> SYNTH;
}