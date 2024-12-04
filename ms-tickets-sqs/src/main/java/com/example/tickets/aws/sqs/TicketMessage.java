package com.example.tickets.aws.sqs;

import lombok.*;

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
