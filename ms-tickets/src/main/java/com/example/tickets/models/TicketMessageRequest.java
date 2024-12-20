package com.example.tickets.models;

import lombok.Builder;

import java.util.Map;
@Builder
public record TicketMessageRequest(String title, String value, Map<String, String> metadata) {
}
