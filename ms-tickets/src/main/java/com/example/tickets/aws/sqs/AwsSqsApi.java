package com.example.tickets.aws.sqs;

import com.example.tickets.config.ISqsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

public interface AwsSqsApi {
    List<Message> receiveMessages();

    void deleteMessage(Message message);

}

@Component
@Slf4j
class SqsMessageHandlerImpl implements AwsSqsApi {
    private final String queueUrl;
    private final SqsClient sqsClient;
    private final ISqsProperties sqsProperties;

    SqsMessageHandlerImpl(SqsClient sqsClient, ISqsProperties sqsProperties) {
        this.sqsClient = sqsClient;
        this.sqsProperties = sqsProperties;
        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(sqsProperties.getQueueName())
                .build();
        this.queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
    }

    @Override
    public void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
        log.info("Deleted {}", message);
    }

    @Override
    public List<Message> receiveMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(this.queueUrl)
                .maxNumberOfMessages(sqsProperties.getMaxNumberOfMessages())
                .visibilityTimeout(sqsProperties.getVisibilityTimeOut())
                .waitTimeSeconds(sqsProperties.getWaitTimeSeconds()).build();
        return sqsClient.receiveMessage(receiveMessageRequest).messages();
    }
}
