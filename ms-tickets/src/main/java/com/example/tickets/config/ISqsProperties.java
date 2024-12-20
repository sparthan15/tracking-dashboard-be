package com.example.tickets.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public interface ISqsProperties {
    String getQueueName();
    int getWaitTimeSeconds();
    int getVisibilityTimeOut();
    int getMaxNumberOfMessages();
}
@Configuration
@ConfigurationProperties(prefix = "cloud.aws.sqs.tickets")
@Data
class ISqsPropertiesImpl implements ISqsProperties {
    private String queueName;
    private int waitTimeSeconds;
    private int visibilityTimeOut;
    private int maxNumberOfMessages;
}