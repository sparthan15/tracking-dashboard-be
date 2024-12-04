package com.example.tickets.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveMongoRepository<MessageEntity, String> {
    Flux<MessageEntity> findGroupedByTitle();
}
