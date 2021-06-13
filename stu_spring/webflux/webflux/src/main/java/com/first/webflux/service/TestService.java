package com.first.webflux.service;

import com.first.webflux.dto.TestListResponse;
import com.first.webflux.dto.TestRequest;
import com.first.webflux.dto.TestResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface TestService {
    Mono<TestResponse> findById(String id);
    Mono<TestListResponse> findAll(ServerRequest request);
    Mono<Void> save(TestRequest request);
}
