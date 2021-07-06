package com.webflux.auth.domain.auth.router;

import com.webflux.auth.domain.auth.handler.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Component
public class AuthRouter {

    private final AuthHandler authHandler;

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return route().path("/auth",
                builder -> builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                        .POST("", authHandler::generateToken)))
                .build();
    }
}
