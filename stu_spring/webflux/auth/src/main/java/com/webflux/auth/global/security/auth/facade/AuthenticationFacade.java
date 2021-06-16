package com.webflux.auth.global.security.auth.facade;

import com.webflux.auth.domain.user.entity.User;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface AuthenticationFacade {
    Mono<String> getUserEmail(ServerRequest request);
}
