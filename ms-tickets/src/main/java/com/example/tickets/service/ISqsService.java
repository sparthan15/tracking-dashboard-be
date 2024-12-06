package com.example.tickets.service;

import com.example.tickets.aws.sqs.TicketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;

public interface ISqsService {

    List<TicketMessage> pollMessages();
}

@Component
@RequiredArgsConstructor
@Slf4j
class AwsSqsServiceImpl implements ISqsService {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    @Value("${cloud.aws.sqs.tickets.queue-name}")
    private String queueName;

    @Override
    public List<TicketMessage> pollMessages() {
        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();

        String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
        HashMap<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS, "20");
        SetQueueAttributesRequest setAttrsRequest = SetQueueAttributesRequest.builder()
                .queueUrl(queueUrl)
                .attributes(attributes)
                .build();
        sqsClient.setQueueAttributes(setAttrsRequest);
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .waitTimeSeconds(20)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
        log.info("Messages received {}", messages.size());
        return messages.stream()
                .parallel()
                .map(this::toTicketMessage)
                .peek(m -> log.info("Received message {}", m))
                .toList();
    }

    private TicketMessage toTicketMessage(Message message) {
        try {
            return objectMapper.readValue(message.body(), TicketMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMessage(Message message, String queueUrl) {
        try {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteMessageRequest);

        } catch (SqsException e) {
            log.error("Error deleting message {}", e.awsErrorDetails().errorMessage());
        }
    }
}
