package com.webflux.auth.domain.user.router;

import com.webflux.auth.domain.user.handler.UserHandler;
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
public class UserRouter {

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRoute() {
        return route().path("/user",
                builder -> builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                        .POST("", userHandler::saveUser)
                        .GET("", userHandler::getUserList)))
                .build();
    }

}
