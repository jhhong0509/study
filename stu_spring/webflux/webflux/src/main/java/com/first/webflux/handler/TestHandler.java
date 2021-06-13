package com.first.webflux.handler;

import com.first.webflux.dto.TestRequest;
import com.first.webflux.exception.TestAlreadyExistException;
import com.first.webflux.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class TestHandler {
    private final TestService testService;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TestRequest.class)
                .flatMap(request -> ServerResponse.created(URI.create("/test"))
                        .build(testService.save(request)));
    }
}
