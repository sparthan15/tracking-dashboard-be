package com.example.tickets.controller;

import java.util.Map;

public record MessageRequest(String title, String value, Map<String, String> metadata) {
}
