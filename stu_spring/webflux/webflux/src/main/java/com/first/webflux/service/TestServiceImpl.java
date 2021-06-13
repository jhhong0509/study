package com.first.webflux.service;

import com.first.webflux.dto.TestRequest;
import com.first.webflux.entity.Test;
import com.first.webflux.entity.TestRepository;
import com.first.webflux.exception.TestAlreadyExistException;
import com.first.webflux.exception.TestNotFoundException;
import lombok.RequiredArgsConstructor;
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
        return testRepository.existsById(testRequest.getId())
                .filter(bool -> !bool)
                .switchIfEmpty(Mono.error(new TestAlreadyExistException()))
                .flatMap(bool -> createTest(testRequest));
    }

    private Mono<Void> createTest(TestRequest testRequest) {
        return testRepository.save(
                Test.builder()
                        .content(testRequest.getContent())
                        .title(testRequest.getTitle())
                        .id(testRequest.getId())
                        .build())
                .then();
    }

}
