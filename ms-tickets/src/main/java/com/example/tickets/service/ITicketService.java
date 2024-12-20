package com.example.tickets.service;

import com.example.tickets.models.TicketMessageRequest;
import com.example.tickets.models.TicketResponse;
import reactor.core.publisher.Flux;

import java.time.LocalDate;


public interface ITicketService {

    Flux<TicketResponse> fetchAllTicketsByDateFromDB(LocalDate date);

    void saveTicketForTodayIfNotPresent(TicketMessageRequest ticketMessageRequest);
}




