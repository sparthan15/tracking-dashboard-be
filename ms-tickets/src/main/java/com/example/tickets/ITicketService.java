package com.example.tickets;

import com.example.tickets.controllers.MessageRequest;
import com.example.tickets.persistence.MessageEntity;
import com.example.tickets.persistence.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface ITicketService {

    Mono<MessageEntity> publishMessage(MessageRequest message);
}

@Service
@Slf4j
@RequiredArgsConstructor
class TicketServiceImpl implements ITicketService {
    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<MessageEntity> publishMessage(MessageRequest messageRequest) {
         return messageRepository.save(new MessageEntity(messageRequest.title(),
                messageRequest.value(), messageRequest.metadata()));
    }
}