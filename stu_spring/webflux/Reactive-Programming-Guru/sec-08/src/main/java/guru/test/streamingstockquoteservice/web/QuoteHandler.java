package guru.test.streamingstockquoteservice.web;

import guru.test.streamingstockquoteservice.model.Quote;
import guru.test.streamingstockquoteservice.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class QuoteHandler {

    private final QuoteService quoteService;

    public Mono<ServerResponse> fetchQuotes(ServerRequest request) {
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));

        return ok().contentType(APPLICATION_JSON)
                .body(quoteService.fetchQuoteStream(Duration.ofMillis(100L))
                        .take(size), Quote.class);
    }

    public Mono<ServerResponse> streamQuotes(ServerRequest request) {
        return ok().contentType(APPLICATION_NDJSON)
                .body(quoteService.fetchQuoteStream(Duration.ofMillis(100L)), Quote.class);
    }
}
