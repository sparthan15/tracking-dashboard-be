package com.example.tickets.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
}
