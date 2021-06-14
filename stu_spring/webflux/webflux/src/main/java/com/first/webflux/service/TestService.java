package com.first.webflux.service;

import com.first.webflux.dto.TestListResponse;
import com.first.webflux.dto.TestRequest;
import com.first.webflux.dto.TestResponse;
import com.first.webflux.dto.TestUpdateRequest;
import reactor.core.publisher.Mono;

public interface TestService {
    Mono<TestResponse> findById(String id);
    Mono<TestListResponse> findAll();
    Mono<Void> save(TestRequest request);
    Mono<Void> delete(String id);
    Mono<Void> update(String id, TestUpdateRequest request);
}
