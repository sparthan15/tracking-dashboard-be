package com.example.tickets.service;

import com.example.tickets.controller.TicketResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static com.example.tickets.controller.TicketResponse.Severity.*;

public interface TicketService {

    Flux<TicketResponse> getTickets();

    @Service
    class TicketServiceDefault implements TicketService {

        @Override
        public Flux<TicketResponse> getTickets() {
            return Flux.just(TicketResponse.builder()
                            .icon("nc-cart-simple")
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
                            .title("Messages 2")
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
}

