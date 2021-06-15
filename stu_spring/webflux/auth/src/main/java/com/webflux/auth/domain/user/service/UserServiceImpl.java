package com.webflux.auth.domain.user.service;

import com.webflux.auth.domain.user.entity.User;
import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.domain.user.exception.UserAlreadyExistException;
import com.webflux.auth.domain.user.payload.request.CreateUserRequest;
import com.webflux.auth.domain.user.payload.response.UserListResponse;
import com.webflux.auth.domain.user.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Void> createUser(CreateUserRequest request) {
        return buildUser(request)
                .flatMap(userRepository::save)
                .onErrorResume(error -> Mono.error(UserAlreadyExistException::new))
                .then();
    }

    @Override
    public Mono<UserListResponse> getUserList() {
        return userRepository.findAll()
                .flatMap(this::buildUserResponse)
                .collectList()
                .flatMap(user -> Mono.just(new UserListResponse(user)));
    }

    private Mono<UserResponse> buildUserResponse(User user) {
        return Mono.just(UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build());
    }

    private Mono<User> buildUser(CreateUserRequest request) {
        return Mono.just(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }

}
