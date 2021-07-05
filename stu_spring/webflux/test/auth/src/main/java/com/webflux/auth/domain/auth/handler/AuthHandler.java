package com.webflux.auth.domain.auth.handler;

import com.webflux.auth.domain.auth.payload.request.AuthRequest;
import com.webflux.auth.domain.auth.payload.response.TokenResponse;
import com.webflux.auth.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class AuthHandler {

    private final AuthService authService;

    public Mono<ServerResponse> generateToken(ServerRequest request) {
        Mono<TokenResponse> result = request.bodyToMono(AuthRequest.class)
                .flatMap(authService::generateToken);

        return ServerResponse.created(URI.create("/auth")).body(result, TokenResponse.class);
    }
}
