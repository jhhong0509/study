package guru.jhhong.rabbitmqexample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ReactiveObjectMapper {

    private final DataBufferFactory dataBufferFactory;
    private final Jackson2JsonEncoder jsonEncoder;
    private final Jackson2JsonDecoder jsonDecoder;

    public Flux<DataBuffer> encodeValue(Class<?> target, Mono<Object> targetObj) {
        ResolvableType type = ResolvableType.forType(target);
        if(jsonEncoder.canEncode(type, null)) {
            return jsonEncoder.encode(targetObj, dataBufferFactory,
                    ResolvableType.forClass(target), null, null);
        }
        return null;
    }

    public Mono<?> decodeValue(Class<?> target, Flux<DataBuffer> dataBufferFlux) {
        ResolvableType type = ResolvableType.forType(target);
        if(jsonDecoder.canDecode(type, null)) {
            return jsonDecoder.decodeToMono(dataBufferFlux, type,
                    null, null);
        }
        return null;
    }

}
