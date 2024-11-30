package com.example.tickets.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;

@Builder
public record TicketResponse(String title, String value, Severity severity, String icon) {

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
}