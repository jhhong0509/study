package com.webflux.auth.global.security.auth;

import ch.qos.logback.core.subst.Token;
import com.webflux.auth.domain.auth.exception.InvalidTokenException;
import com.webflux.auth.global.security.jwt.JwtTokenProvider;
import com.webflux.auth.global.security.jwt.TokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtVerifier {

    private final JwtTokenProvider jwtTokenProvider;

    public Mono<JwtTokenInformation> check(String token) {
        return Mono.just(jwtTokenProvider.parseToken(token))
                .flatMap(this::validateToken)
                .flatMap(claims -> Mono.just(new JwtTokenInformation(token, getTokenType(claims), claims)));
    }

    private TokenType getTokenType(Claims claims) {
        return TokenType.valueOf(claims.get("type").toString().toUpperCase());
    }

    private Mono<Claims> validateToken(Mono<Claims> claims) {
        return claims
                .filter(claim ->
                        claim.get("type").equals(TokenType.ACCESS.getType()) &&
                                claim.getExpiration().after(new Date()))
                .switchIfEmpty(Mono.error(InvalidTokenException::new));
    }
}
