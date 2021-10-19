package guru.jhhong.rabbitmqexample.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "quotes";
//
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE, false);
//    }

    @Bean
    public Mono<Connection> connectionMono(CachingConnectionFactory connectionFactory) {
        return Mono.fromCallable(() -> connectionFactory
                .getRabbitConnectionFactory().newConnection());
    }

    @Bean
    public Sender sender(Mono<Connection> mono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(mono));
    }

    @Bean
    public Receiver receiver(Mono<Connection> connectionMono) {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }
}
