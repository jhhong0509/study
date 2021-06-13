//package com.first.webflux.router;
//
//import com.first.webflux.handler.TestHandler;
//import com.first.webflux.service.TestService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.*;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.*;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
//@Configuration
//@RequiredArgsConstructor
//public class TestRouter {
//
//    private final TestHandler testHandler;
//
//    @Bean
//    public RouterFunction<ServerResponse> getTestByIdRoute() {
//        return route(GET("/test/{id}")
//                        .and(accept(MediaType.APPLICATION_JSON)), testHandler::getTest);
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> getTestsRoute() {
//        return route(GET("/tests")
//                .and(accept(MediaType.APPLICATION_JSON)), testHandler::getTestList);
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> createTestRoute() {
//        return route(POST("/test")
//                .and(accept(MediaType.APPLICATION_JSON)), testHandler::save);
//    }
//}
