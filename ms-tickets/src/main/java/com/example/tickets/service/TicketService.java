package com.example.tickets.service;

import com.example.tickets.controller.TicketResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.tickets.controller.TicketResponse.Severity.*;

public interface TicketService {

    Set<TicketResponse> getTickets();
}

@Service
class TicketServiceDefault implements TicketService {

    @Override
    public Set<TicketResponse> getTickets() {
        return Set.of(TicketResponse.builder()
                        .icon("nc icon")
                        .severity(SUCCESS)
                        .title("Capacity")
                        .value("155GB")
                        .build(),
                TicketResponse.builder()
                        .icon("nc-cart-simple")
                        .severity(DANGER)
                        .title("Revenue")
                        .value("$1000")
                        .build(),
                TicketResponse.builder()
                        .severity(WARNING)
                        .icon("nc-chat-33")
                        .title("Messages")
                        .value("22")
                        .build(),
                TicketResponse.builder()
                        .severity(SUCCESS)
                        .icon("nc-chat-33")
                        .title("Volume")
                        .value("2222")
                        .build());
    }
}
