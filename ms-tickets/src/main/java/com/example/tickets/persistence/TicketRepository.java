package com.example.tickets.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface TicketRepository extends ReactiveMongoRepository<TicketEntity, TicketEntityId> {
    Flux<TicketEntity> findAllByIdInsertionDate(LocalDate insertionDate);

}
