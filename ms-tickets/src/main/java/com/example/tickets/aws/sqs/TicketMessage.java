package com.example.tickets.aws.sqs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketMessage {

    private String id;
    private String title;
    private String value;
    private LocalDateTime createdAt;
}
