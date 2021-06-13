package com.first.webflux.handler;

import com.first.webflux.dto.TestListResponse;
import com.first.webflux.dto.TestRequest;
import com.first.webflux.dto.TestResponse;
import com.first.webflux.exception.TestAlreadyExistException;
import com.first.webflux.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class TestHandler {

    private final TestService testService;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<Void> result = serverRequest.bodyToMono(TestRequest.class)
                .flatMap(testService::save);
        return ServerResponse.created(URI.create("/test")).body(result, Void.class);
    }

    public Mono<ServerResponse> getTest(ServerRequest serverRequest) {
        Mono<TestResponse> result = testService.findById(serverRequest.pathVariable("id"));
        return ServerResponse.ok().body(result, TestResponse.class);
    }

    public Mono<ServerResponse> getTestList(ServerRequest serverRequest) {
        Mono<TestListResponse> result = testService.findAll(serverRequest);
        return ServerResponse.ok().body(result, TestResponse.class);
    }
}
