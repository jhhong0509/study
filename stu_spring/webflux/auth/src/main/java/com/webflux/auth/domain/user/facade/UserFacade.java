package com.webflux.auth.domain.user.facade;

import com.webflux.auth.domain.user.entity.User;
import reactor.core.publisher.Mono;

public interface UserFacade {
    Mono<User> getUser(String email);
}
