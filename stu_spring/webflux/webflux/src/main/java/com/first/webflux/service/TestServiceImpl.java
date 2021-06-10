package com.first.webflux.service;

import com.first.webflux.entity.Test;
import com.first.webflux.entity.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testRepository.findById(request.pathVariable("id")), Test.class);
    }

    @Override
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testRepository.findAll(), Test.class);
    }

    @Override
    public Mono<Void> save(ServerRequest request) {
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(request.bodyToMono(Test.class)
//                        .switchIfEmpty(Mono.empty())
//                        .filter(Objects::nonNull)
//                        .flatMap(test -> Mono.just(testRepository.save(test)))
//                        .flatMap(testMono -> testMono))
//                .switchIfEmpty(notFound().build());
    }

}
