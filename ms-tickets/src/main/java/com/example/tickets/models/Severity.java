package com.example.tickets.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Severity {
    SUCCESS("success"),
    DANGER("danger"),
    WARNING("warning"),
    INFO("info");

    @JsonValue
    public String getSeverityName() {
        return this.severityName;
    }

    private final String severityName;

    Severity(String severityName) {
        this.severityName = severityName;
    }
}
