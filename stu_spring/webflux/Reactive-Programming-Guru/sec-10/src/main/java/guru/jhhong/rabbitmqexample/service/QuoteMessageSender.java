package guru.jhhong.rabbitmqexample.service;

import guru.jhhong.rabbitmqexample.config.RabbitConfig;
import guru.jhhong.rabbitmqexample.config.ReactiveObjectMapper;
import guru.jhhong.rabbitmqexample.model.Quote;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.OutboundMessageResult;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;
import reactor.util.function.Tuple2;

@RequiredArgsConstructor
@Component
public class QuoteMessageSender {

    private final ReactiveObjectMapper objectMapper;
    private final Sender sender;

    @SneakyThrows
    public Mono<Void> sendQuoteMessage(Quote quote) {
        return objectMapper.encodeValue(quote.getClass(), Mono.just(quote))
                .map(dataBuffer -> Mono.just(new OutboundMessage("", RabbitConfig.QUEUE,
                        dataBuffer.asByteBuffer().array())))
                .flatMap(sender::send)
//                .flatMap(res -> sender.declareQueue(QueueSpecification.queue(RabbitConfig.QUEUE)))        ?? 왜 있는건지 모르겠음
                .doOnError(Throwable::printStackTrace)
                .then();
    }

}
