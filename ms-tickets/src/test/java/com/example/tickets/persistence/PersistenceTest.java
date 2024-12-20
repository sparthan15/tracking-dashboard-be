package com.example.tickets.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.time.LocalDate;

@DataMongoTest
public class PersistenceTest {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testFindAllByExample() {
        var today = LocalDate.now();
        var ticketId = new TicketEntityId("any ticket", today);
        this.ticketRepository.save(TicketEntity.builder()
                .id(TicketEntityId.builder().title("any ticket").insertionDate(today).build()).build()).block();
        var ticket = reactiveMongoTemplate.findById(ticketId, TicketEntity.class).block();

        Assertions.assertThat(ticket.getId()).isEqualTo(ticketId);
    }

    @Test
    void testFindAllByIdInsertionDate() {
        var today = LocalDate.now();
        var ticketId = new TicketEntityId("any ticket", today);
        this.ticketRepository.save(TicketEntity.builder()
                .id(TicketEntityId.builder().title("any ticket").insertionDate(today).build()).build()).block();
        var ticket = this.ticketRepository.findAllByIdInsertionDate(today).blockFirst();
        Assertions.assertThat(ticket.getId()).isEqualTo(ticketId);
    }

    @Test
    void testFindAllById() {
        var today = LocalDate.now();
        var ticketId = new TicketEntityId("any ticket", today);
        this.ticketRepository.save(TicketEntity.builder()
                .id(TicketEntityId.builder().title("any ticket").insertionDate(today).build()).build()).block();
        var ticket = this.ticketRepository.findById(ticketId).block();
        Assertions.assertThat(ticket.getId()).isEqualTo(ticketId);
    }
}
