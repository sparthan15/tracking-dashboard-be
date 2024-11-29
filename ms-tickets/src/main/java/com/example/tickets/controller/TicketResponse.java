package com.example.tickets.controller;

import lombok.Builder;

@Builder
public record TicketResponse(String title, String value, Severity severity, String icon) {

    public enum Severity {
        SUCCESS("success"),
        DANGER("danger"),
        WARNING("warning"),
        INFO("info");

        private String severityName;

        Severity(String severityName) {
            this.severityName = severityName;
        }
    }
}