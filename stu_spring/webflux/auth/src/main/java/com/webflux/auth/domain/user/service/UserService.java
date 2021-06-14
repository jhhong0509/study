package com.webflux.auth.domain.user.service;

import com.webflux.auth.domain.user.payload.request.CreateUserRequest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> createUser(CreateUserRequest request);
}
