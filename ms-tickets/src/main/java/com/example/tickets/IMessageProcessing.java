package com.example.tickets;

import com.example.tickets.models.TicketMessageRequest;
import com.example.tickets.persistence.ITicketMessageRepository;
import com.example.tickets.persistence.TicketMessageEntity;
import com.example.tickets.service.ITicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface IMessageProcessing {

    Mono<TicketMessageEntity> processTicketMessage(TicketMessageRequest message);
}

@Service
@Slf4j
@RequiredArgsConstructor
class MessageProcessingImpl implements IMessageProcessing {
    private final ITicketMessageRepository messageRepository;
    private final ITicketService ticketService;

    @Override
    public Mono<TicketMessageEntity> processTicketMessage(TicketMessageRequest messageRequest) {
        ticketService.saveTicketForTodayIfNotPresent(messageRequest);
        return messageRepository.save(new TicketMessageEntity(messageRequest.title(),
                messageRequest.value(), messageRequest.metadata()));
    }
}