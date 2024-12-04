package com.example.tickets.controllers;

import com.example.tickets.ITicketService;
import com.example.tickets.persistence.MessageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/messages")
@RequiredArgsConstructor
@Slf4j
public class SqsController {

    private final ITicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MessageEntity> publishAndStoreMessage(@RequestBody MessageRequest messageRequest) {
        return ticketService.publishMessage(messageRequest);
    }

}
