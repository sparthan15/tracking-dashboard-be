package com.example.tickets.aws.sqs;

import com.example.tickets.IMessageProcessing;
import com.example.tickets.models.TicketMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.concurrent.ArrayBlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsListener implements DisposableBean {
    private final IMessageProcessing messageProcessingService;
    private final AwsSqsApi awsSqsApi;
    private final ObjectMapper objectMapper;
    private volatile boolean doPolling;

    private final
    ArrayBlockingQueue<Message> messageHoldingQueue = new ArrayBlockingQueue<>(
            1);

    @Override
    public void destroy() {
        this.doPolling = false;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void messageListener() {
        this.doPolling = true;

        while (doPolling) {
            log.info("Polling...");
            try {
                var messages = awsSqsApi.receiveMessages();
                if (messages != null && !messages.isEmpty()) {
                    log.info("Messages {}", messages.size());
                    for (Message message : messages) {
                        messageHoldingQueue.put(message);
                    }
                    try {
                        Message receivedMessage = messageHoldingQueue.poll();
                        log.info("Reading message {}", receivedMessage);
                        var ticketMessageRequest = objectMapper.readValue(receivedMessage.body(),
                                TicketMessageRequest.class);
                        var processedTicket =
                                messageProcessingService.processTicketMessage(ticketMessageRequest);
                        awsSqsApi.deleteMessage(receivedMessage);
                        log.info("Ticket processed {}", processedTicket.block());
                    } catch (Exception e) {
                        log.error("Exception processing message from SQS Queue: {} ",e.getMessage(), e);
                    }
                } else {
                    log.info("No messages found in the queue");
                }
            } catch (Exception e) {
                log.error("Exception while polling SQS Queue: {} ",e.getMessage(),  e);
            }
        }
    }


}
