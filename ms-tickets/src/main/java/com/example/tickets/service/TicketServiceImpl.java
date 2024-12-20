package com.example.tickets.service;

import com.example.tickets.models.TicketMessageRequest;
import com.example.tickets.models.TicketMessageResponse;
import com.example.tickets.models.TicketResponse;
import com.example.tickets.persistence.TicketEntity;
import com.example.tickets.persistence.TicketEntityId;
import com.example.tickets.persistence.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.example.tickets.models.Severity.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements ITicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Flux<TicketResponse> fetchAllTicketsByDateFromDB(LocalDate insertionDate) {
        return ticketRepository.findAllByIdInsertionDate(insertionDate)
                .map(TicketEntity::toResponse);
    }


    @Override
    public void saveTicketForTodayIfNotPresent(TicketMessageRequest ticketMessageRequest) {
        ticketRepository.findById(new TicketEntityId(ticketMessageRequest.title(), LocalDate.now()))
                .switchIfEmpty(Mono.defer(() -> this.ticketRepository.save(TicketEntity.fromRequest(ticketMessageRequest))))
                .subscribe();
    }

    public Flux<TicketMessageResponse> getTickets() {
        return Flux.just(TicketMessageResponse.builder()
                        .icon("nc-cart-simple")
                        .severity(SUCCESS)
                        .title("Capacity")
                        .value("155GB")
                        .build(),
                TicketMessageResponse.builder()
                        .icon("nc-cart-simple")
                        .severity(DANGER)
                        .title("Revenue")
                        .value("$1000")
                        .build(),
                TicketMessageResponse.builder()
                        .severity(WARNING)
                        .icon("nc-chat-33")
                        .title("Messages 2")
                        .value("22")
                        .build(),
                TicketMessageResponse.builder()
                        .severity(SUCCESS)
                        .icon("nc-chat-33")
                        .title("Volume")
                        .value("2222")
                        .build());
    }
}

