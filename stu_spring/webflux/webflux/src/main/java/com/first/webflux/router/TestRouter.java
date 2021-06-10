package com.first.webflux.router;

import com.first.webflux.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TestRouter {

    private final TestService testService;

    @Bean
    public RouterFunction<ServerResponse> getTestByIdRoute() {
        return route(GET("/test/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), testService::findById);
    }

    @Bean
    public RouterFunction<ServerResponse> getTestsRoute() {
        return route(GET("/tests")
                .and(accept(MediaType.APPLICATION_JSON)), testService::findAll);
    }
}
