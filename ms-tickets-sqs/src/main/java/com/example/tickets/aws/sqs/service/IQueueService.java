package com.example.tickets.aws.sqs.service;


import com.example.tickets.aws.sqs.TicketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IQueueService {
    TicketMessage publishMessage(TicketMessage ticketMessage);
}

@Service
@RequiredArgsConstructor
@Slf4j
class AwsSqsService implements IQueueService {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    @Value("${aws.queue-url}")
    private String queueUrl;

    @SneakyThrows
    @Override
    public TicketMessage publishMessage(TicketMessage ticketMessageRequest) {

            ticketMessageRequest.setId(UUID.randomUUID().toString());
            ticketMessageRequest.setCreatedAt(LocalDateTime.now());
            log.info("Request {}", ticketMessageRequest);
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .delaySeconds(1)
                    .queueUrl(queueUrl)
                    .messageBody(objectMapper.writeValueAsString(ticketMessageRequest))
                    .build());
            return ticketMessageRequest;
    }
}