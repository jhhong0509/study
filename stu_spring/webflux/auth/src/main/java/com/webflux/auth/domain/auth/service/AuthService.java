package com.webflux.auth.domain.auth.service;

import com.webflux.auth.domain.auth.payload.request.AuthRequest;
import com.webflux.auth.domain.auth.payload.response.TokenResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<TokenResponse> generateToken(AuthRequest request);
}
