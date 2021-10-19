package guru.test.streamingstockquoteservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class QuoteRouteConfig {

    private final QuoteHandler quoteHandler;

    @Bean
    public RouterFunction<ServerResponse> quoteRoutes() {
        return route().GET("/quotes", accept(APPLICATION_JSON), quoteHandler::fetchQuotes)
                .GET("/quotes", accept(APPLICATION_NDJSON), quoteHandler::streamQuotes)
                .build();
    }
}
