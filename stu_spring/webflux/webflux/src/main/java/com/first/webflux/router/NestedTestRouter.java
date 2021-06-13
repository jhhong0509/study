package com.first.webflux.router;

import com.first.webflux.handler.TestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class NestedTestRouter {

    private final TestHandler testHandler;

    @Bean
    public RouterFunction<ServerResponse> testRoute() {
        return nest(path("/test"),
                route(GET("/{id}"), testHandler::getTest)
                        .andRoute(GET("/"), testHandler::getTestList)
                        .andRoute(POST("/").and(contentType(MediaType.APPLICATION_JSON)), testHandler::save));
    }
}

