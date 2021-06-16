package com.webflux.auth.global.security.jwt;

import com.webflux.auth.domain.auth.exception.InvalidTokenException;
import com.webflux.auth.global.security.auth.AuthenticationToken;
import com.webflux.auth.global.security.auth.JwtVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtTokenExtractor implements ServerAuthenticationConverter {

    private final JwtVerifier jwtVerifier;

    private Mono<String> extractTokenFromBearer(String token) {
        return Mono.justOrEmpty(token.substring(7));
    }

    private Mono<String> getToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(
                exchange.getRequest().getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION)
        );
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(this::getToken)
                .flatMap(this::extractTokenFromBearer)
                .flatMap(jwtVerifier::check)
                .flatMap(jwtTokenInformation -> Mono.just(
                        new AuthenticationToken(
                                jwtTokenInformation.getTokenType(),
                                jwtTokenInformation.getClaims().getSubject(),
                                jwtTokenInformation.getToken()
                                )
                        )
                );
    }
}
