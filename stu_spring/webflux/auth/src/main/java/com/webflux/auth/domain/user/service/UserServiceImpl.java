package com.webflux.auth.domain.user.service;

import com.webflux.auth.domain.user.entity.User;
import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.domain.user.exception.UserAlreadyExistException;
import com.webflux.auth.domain.user.payload.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<Void> createUser(CreateUserRequest request) {
        return userRepository.existsById(request.getEmail())
                .filter(bool -> !bool)
                .flatMap(bool -> buildUser(request))
                .flatMap(userRepository::save)
                .switchIfEmpty(Mono.error(UserAlreadyExistException::new))
                .then();
    }

    private Mono<User> buildUser(CreateUserRequest request) {
        return Mono.just(User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
    }
}
