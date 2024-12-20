package com.example.tickets;

import com.example.tickets.aws.sqs.SqsListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest

class TicketsApplicationTests {

    @MockBean
    private SqsListener sqsListener;

    @Test
    void contextLoads() {
    }

}
