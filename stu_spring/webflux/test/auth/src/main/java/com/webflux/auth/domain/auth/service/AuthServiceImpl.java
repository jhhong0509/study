package com.webflux.auth.domain.auth.service;

import com.webflux.auth.domain.auth.payload.request.AuthRequest;
import com.webflux.auth.domain.auth.payload.response.TokenResponse;
import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.domain.user.exception.UserNotFoundException;
import com.webflux.auth.global.security.jwt.JwtTokenProvider;
import com.webflux.auth.global.security.jwt.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<TokenResponse> generateToken(AuthRequest request) {
        Mono<String> accessToken = userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .flatMap(user -> jwtTokenProvider.generateToken(request.getEmail(), TokenType.ACCESS))
                .switchIfEmpty(Mono.error(UserNotFoundException::new));

        return accessToken
                .flatMap(access -> jwtTokenProvider.generateToken(request.getEmail(), TokenType.REFRESH))
                .flatMap(refresh -> buildToken(refresh, accessToken));
    }

    private Mono<TokenResponse> buildToken(String refreshToken, Mono<String> accessToken) {
        return accessToken
                .flatMap(access -> Mono.just(new TokenResponse(access, refreshToken)));
    }

}
