package com.example.tickets.persistence;

import com.example.tickets.models.Severity;
import com.example.tickets.models.TicketMessageRequest;
import com.example.tickets.models.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Document(collection = "tickets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketEntity {
    @Id
    private TicketEntityId id;
    private String icon;
    private Severity severity;
    private String value;
    private Instant createdAt;

    public TicketResponse toResponse() {
        return TicketResponse.builder()
                .title(this.getId().getTitle())
                .value(this.getValue())
                .icon(this.getIcon())
                .severity(this.getSeverity())
                .insertionDate(this.getId().getInsertionDate())
                .build();
    }

    public static TicketEntity fromRequest(TicketMessageRequest ticketMessageRequest) {
        return TicketEntity.builder()
                .id(TicketEntityId.builder()
                        .title(ticketMessageRequest.title())
                        .insertionDate(LocalDate.now())
                        .build())
                .icon("nc-chat-33")
                .severity(Severity.SUCCESS)
                .build();
    }

    enum Status {
        ENABLE,
        DISABLED
    }
}


