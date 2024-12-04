package com.example.tickets.aws.sqs.service;


import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.example.tickets.aws.sqs.TicketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IQueueService {
    TicketMessage publishMessage(TicketMessage ticketMessage);
}

@Service
@RequiredArgsConstructor
@Slf4j
class AwsSqsService implements IQueueService {
    private final AmazonSQS amazonSQSClient;
    private final ObjectMapper objectMapper;
    @Value("${aws.queuename}")
    private String queueName;

    @Override
    public TicketMessage publishMessage(TicketMessage ticketMessageRequest) {
        try {
            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(queueName);
            ticketMessageRequest.setId(UUID.randomUUID().toString());
            ticketMessageRequest.setCreatedAt(LocalDateTime.now());
            log.info("Request {}", ticketMessageRequest);
            amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), objectMapper.writeValueAsString(ticketMessageRequest));
            return ticketMessageRequest;
        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
            return null;
        }
    }
}