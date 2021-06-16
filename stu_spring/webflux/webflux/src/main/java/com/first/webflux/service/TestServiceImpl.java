package com.first.webflux.service;

import com.first.webflux.dto.TestListResponse;
import com.first.webflux.dto.TestRequest;
import com.first.webflux.dto.TestResponse;
import com.first.webflux.dto.TestUpdateRequest;
import com.first.webflux.entity.Test;
import com.first.webflux.entity.TestRepository;
import com.first.webflux.exception.TestAlreadyExistException;
import com.first.webflux.exception.TestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public Mono<TestResponse> findById(String id) {
        return testRepository.findById(id)
                .flatMap(test -> Mono.just(TestResponse.builder()
                        .content(test.getContent())
                        .title(test.getTitle())
                        .id(test.getId())
                        .build()))
                .switchIfEmpty(Mono.error(TestNotFoundException::new));
    }

    @Override
    public Mono<TestListResponse> findAll() {
        return testRepository.findAll()
                .flatMap(this::buildTestResponse)
                .collectList()
                .flatMap(testResponses -> Mono.just(TestListResponse.builder()
                        .testResponses(testResponses)
                        .build()));
    }

    @Override
    public Mono<Void> save(TestRequest testRequest) {
        return testRepository.existsById(testRequest.getId())
                .filter(bool -> !bool)
                .switchIfEmpty(Mono.error(new TestAlreadyExistException()))
                .flatMap(bool -> createTest(testRequest));
    }

    @Override
    public Mono<Void> delete(String id) {
        return testRepository.findById(id)
                .switchIfEmpty(Mono.error(TestNotFoundException::new))
                .flatMap(testRepository::delete)
                .then();
    }

    @Override
    public Mono<Void> update(String id, TestUpdateRequest request) {
        return testRepository.findById(id)
                .flatMap(test -> {
                    test.update(request);
                    return testRepository.save(test);
                })
                .switchIfEmpty(Mono.error(TestNotFoundException::new))
                .then();
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

    private Mono<TestResponse> buildTestResponse(Test test) {
        return Mono.just(TestResponse.builder()
                .content(test.getContent())
                .title(test.getTitle())
                .id(test.getId())
                .build());
    }

}