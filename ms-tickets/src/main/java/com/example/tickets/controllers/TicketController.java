package com.example.tickets.controllers;

import com.example.tickets.models.TicketResponse;
import com.example.tickets.service.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final ITicketService ticketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<TicketResponse> getTickets() {
        return ticketService.fetchAllTicketsByDateFromDB(LocalDate.now());
    }
}
