package guru.test.streamingstockquoteservice.service;

import guru.test.streamingstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteService {
    Flux<Quote> fetchQuoteStream(Duration period);
}
