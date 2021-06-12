package com.first.webflux.service;

import com.first.webflux.dto.TestRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface TestService {
    Mono<ServerResponse> findById(ServerRequest request);
    Mono<ServerResponse> findAll(ServerRequest request);
    Mono<Void> save(TestRequest request);
}
