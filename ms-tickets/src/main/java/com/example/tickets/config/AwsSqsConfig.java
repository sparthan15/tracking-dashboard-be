package com.example.tickets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
class AwsSqsConfig {

    @Bean
    public SqsClient sqsClient(){
        return SqsClient.create();
    }
}
