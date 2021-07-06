package com.webflux.auth.global.security.auth;

import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .flatMap(auth -> Mono.just(auth.getName()))
                .flatMap(userRepository::findByEmail)
                .flatMap(user -> Mono.just(authentication))
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }

}
