package com.webflux.auth.domain.blog.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
}
