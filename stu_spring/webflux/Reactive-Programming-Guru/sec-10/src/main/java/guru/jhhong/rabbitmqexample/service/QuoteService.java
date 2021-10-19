package guru.jhhong.rabbitmqexample.service;

import guru.jhhong.rabbitmqexample.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteService {
    Flux<Quote> fetchQuoteStream(Duration period);
}
