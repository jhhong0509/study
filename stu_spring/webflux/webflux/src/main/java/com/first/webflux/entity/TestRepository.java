package com.first.webflux.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TestRepository extends ReactiveMongoRepository<Test, String> {
}
