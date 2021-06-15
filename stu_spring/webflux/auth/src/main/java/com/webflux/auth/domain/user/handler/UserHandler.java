package com.webflux.auth.domain.user.handler;

import com.webflux.auth.domain.user.payload.request.CreateUserRequest;
import com.webflux.auth.domain.user.payload.response.UserListResponse;
import com.webflux.auth.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<Void> result = request.bodyToMono(CreateUserRequest.class)
                .flatMap(userService::createUser);

        return ServerResponse.created(URI.create("/user"))
                .body(result, Void.class);
    }

    public Mono<ServerResponse> getUserList(ServerRequest request) {
        Mono<UserListResponse> result = userService.getUserList();

        return ServerResponse.ok().body(result, UserListResponse.class);
    }
}
