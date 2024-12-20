package com.example.tickets.models;

import lombok.Builder;

@Builder
public record TicketMessageResponse(String title, String value, Severity severity, String icon) {


}