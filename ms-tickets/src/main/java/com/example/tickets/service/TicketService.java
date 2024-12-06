package com.example.tickets.service;

import com.example.tickets.aws.sqs.TicketMessage;
import com.example.tickets.controllers.TicketResponse;
import com.example.tickets.persistence.MessageEntity;
import com.example.tickets.persistence.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.example.tickets.controllers.TicketResponse.Severity.*;

public interface TicketService {

    Flux<TicketResponse> getTickets();

    Flux<TicketResponse> findGroupedByTitle();

    @Service
    @RequiredArgsConstructor
    @Slf4j
    class TicketServiceDefault implements TicketService {
        private final MessageRepository messageRepository;
        private final ISqsService sqsService;

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

        @Override
        public Flux<TicketResponse> findGroupedByTitle() {
            List<TicketMessage> ticketMessages = sqsService.pollMessages();
            Flux<MessageEntity> responseFlux = this.messageRepository.findGroupedByTitle();
            responseFlux.doOnNext(m -> log.info(m.getValue()));

            return null;
        }

    }
}

