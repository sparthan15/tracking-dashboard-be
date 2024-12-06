package com.example.tickets.aws.sqs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsSqsConfig {

    @Bean
    public SqsClient sqsClient(){
        return SqsClient.create();
    }
}
