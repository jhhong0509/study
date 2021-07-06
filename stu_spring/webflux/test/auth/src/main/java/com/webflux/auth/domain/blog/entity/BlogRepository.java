package com.webflux.auth.domain.blog.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
    @Query("{ id: { $exists: true }}")
    Flux<Blog> findAllBy(Pageable pageable);
}
