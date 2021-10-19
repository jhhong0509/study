package guru.jhhong.rabbitmqexample.service;

import guru.jhhong.rabbitmqexample.model.Quote;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final MathContext mathContext = new MathContext(2);
    private final Random random = new Random();
    private final List<Quote> quotes = new ArrayList<>();

    public QuoteServiceImpl() {
        quotes.add(new Quote("APPLE", 134.16));
        quotes.add(new Quote("TESLA", 739.14));
        quotes.add(new Quote("NTFLX", 546.25));
        quotes.add(new Quote("PLTR", 92.21));
        quotes.add(new Quote("SMSU", 423.83));
    }

    @Override
    public Flux<Quote> fetchQuoteStream(Duration period) {
        return Flux.generate(() -> 0,
                (BiFunction<Integer, SynchronousSink<Quote>, Integer>) (index, sink) -> {
                    Quote updatedQuote = updateQuote(this.quotes.get(index));
                    sink.next(updatedQuote);
                    return ++index % this.quotes.size();
                })
                .zipWith(Flux.interval(period))
                .map(Tuple2::getT1)
                .map(quote -> {
                    quote.updateInstant(Instant.now());
                    return quote;
                })
                .log("guru.springframework.service.QuoteGeneratorService");
    }

    private Quote updateQuote(Quote quote) {
        BigDecimal updatedPrice = quote.getPrice()
                .multiply(BigDecimal.valueOf(0.05 * random.nextDouble()), mathContext);

        return new Quote(quote.getTicker(), updatedPrice);
    }
}
