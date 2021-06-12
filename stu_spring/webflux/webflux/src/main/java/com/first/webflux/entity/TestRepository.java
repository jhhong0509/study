package com.first.webflux.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends ReactiveMongoRepository<Test, String> {
}
