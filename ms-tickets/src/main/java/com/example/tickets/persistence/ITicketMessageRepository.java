package com.example.tickets.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ITicketMessageRepository extends ReactiveMongoRepository<TicketMessageEntity, String> {
    Flux<TicketMessageEntity> findGroupedByTitle();
}
