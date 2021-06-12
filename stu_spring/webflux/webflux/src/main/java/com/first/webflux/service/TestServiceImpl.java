package com.first.webflux.service;

import com.first.webflux.dto.TestRequest;
import com.first.webflux.entity.Test;
import com.first.webflux.entity.TestRepository;
import com.first.webflux.exception.TestAlreadyExistException;
import com.first.webflux.exception.TestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testRepository.findById(request.pathVariable("id")), Test.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testRepository.findAll(), Test.class);
    }

    @Override
    public Mono<Void> save(TestRequest testRequest) {
        return testRepository.findById(testRequest.getId())
                .switchIfEmpty(Mono.just(Test.builder()
                        .id(testRequest.getId())
                        .title(testRequest.getTitle())
                        .content(testRequest.getContent())
                        .build()))
                .flatMap(testRepository::save)
                .then();
    }

}
