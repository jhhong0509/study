package guru.test.streamingstockquoteservice.service;

import guru.test.streamingstockquoteservice.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class QuoteServiceImplTest {

    QuoteService service;

    @BeforeEach
    void setUp() {
        service = new QuoteServiceImpl();
    }

    @Test
    void fetchQuoteStream() throws InterruptedException {
        Flux<Quote> quoteFlux = service.fetchQuoteStream(Duration.ofMillis(1000L));

        Consumer<Quote> quoteConsumer = System.out::println;
        Consumer<Throwable> throwableConsumer = e -> System.out.println(e.getMessage());

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable done = countDownLatch::countDown;

        quoteFlux.take(30)
                .subscribe(quoteConsumer, throwableConsumer, done);

        countDownLatch.await();
    }
}