package guru.jhhong.rabbitmqexample.service;

import guru.jhhong.rabbitmqexample.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
public class QuoteRunner implements CommandLineRunner {

    private final QuoteService quoteService;
    private final QuoteMessageSender quoteMessageSender;
    private final Receiver receiver;

    @Override
    public void run(String... args) throws InterruptedException {

        quoteService.fetchQuoteStream(Duration.ofMillis(100L))
                .take(25)
                .log("got quote")
                .flatMap(quoteMessageSender::sendQuoteMessage)
                .subscribe(res -> System.out.println("Success"),
                        Throwable::printStackTrace,
                        () -> System.out.println("Done!"));
        
        AtomicInteger count = new AtomicInteger(0);

        receiver.consumeAutoAck(RabbitConfig.QUEUE)
                .log("Message Delivered")
                .subscribe(msg -> {
                    System.out.println("consumed:" + count + new String(msg.getBody()));
                    count.addAndGet(1);
                }, Throwable::printStackTrace);
    }
}
