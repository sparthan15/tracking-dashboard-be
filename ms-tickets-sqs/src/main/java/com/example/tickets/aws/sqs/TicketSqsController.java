package com.example.tickets.aws.sqs;

import com.example.tickets.aws.sqs.service.IQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/tickets/messages")
@RequiredArgsConstructor
@Slf4j
public class TicketSqsController {
    private final IQueueService queueService;

    private String queueName;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketMessage publishMessage(@RequestBody TicketMessage ticketMessageRequest) {
        return queueService.publishMessage(ticketMessageRequest);
    }
}
