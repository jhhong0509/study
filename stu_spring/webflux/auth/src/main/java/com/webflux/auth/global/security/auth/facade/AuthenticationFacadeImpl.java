package com.webflux.auth.global.security.auth.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Mono<String> getUserEmail(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> Mono.just(principal.getName()));
    }

}
