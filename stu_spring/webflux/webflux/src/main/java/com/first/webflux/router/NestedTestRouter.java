package com.first.webflux.router;

import com.first.webflux.handler.TestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class NestedTestRouter {

    private final TestHandler testHandler;

    @Bean
    public RouterFunction<ServerResponse> testRoute() {
        return route().path("/test", builder ->
                builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                        .POST("", testHandler::save)
                        .GET("/{id}", testHandler::getTest)
                        .GET("", testHandler::getTestList)
                        .PATCH("/{id}", testHandler::updateTest)
                        .DELETE("/{id}", testHandler::deleteTest)))
                .build();
    }
}

