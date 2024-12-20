package com.example.tickets.models;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TicketResponse(String title, String value, String icon, Severity severity,
                             LocalDate insertionDate) {
}
