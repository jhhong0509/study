package com.webflux.auth.global.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String str = "{\n" +
                "  \"status\": 401,\n" +
                "  \"message\": \"Not Authenticated.\"\n" +
                "}";

        var buffer = exchange.getResponse().bufferFactory().wrap(str.getBytes());
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
