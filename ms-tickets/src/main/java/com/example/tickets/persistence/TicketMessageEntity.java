package com.example.tickets.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ticket_messages")
@Builder
public class TicketMessageEntity {
    @Id
    private String id;
    private String title;
    private String value;
    private Map<String, String>metadata;
    private Instant createdAt = Instant.now();

    public TicketMessageEntity(String title, String value, Map<String, String>metadata){
        this.title = title;
        this.value = value;
        this.metadata =metadata;
    }

}