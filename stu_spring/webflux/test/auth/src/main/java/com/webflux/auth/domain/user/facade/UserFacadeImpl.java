package com.webflux.auth.domain.user.facade;

import com.webflux.auth.domain.user.entity.User;
import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUser(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }
}
