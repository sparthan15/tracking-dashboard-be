package com.example.tickets.controller;

import com.example.tickets.persistence.Message;
import com.example.tickets.persistence.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageRepository messageRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Message> addMessage(@RequestBody MessageRequest messageRequest) {
        log.info("Yea {}",messageRequest);
        return messageRepository.save(new Message(messageRequest.title(), messageRequest.value(),
                messageRequest.metadata()));
    }

}
