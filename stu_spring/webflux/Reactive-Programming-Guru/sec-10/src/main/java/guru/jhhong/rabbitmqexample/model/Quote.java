package guru.jhhong.rabbitmqexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quote {

    private final static MathContext MATH_CONTEXT = new MathContext(2);

    private String ticker;

    private BigDecimal price;

    private Instant instant;

    public Quote(String ticker, Double price) {
        this.ticker = ticker;
        this.price = BigDecimal.valueOf(price);
    }

    public Quote(String ticker, BigDecimal price) {
        this.ticker = ticker;
        this.price = price;
    }

    public Quote updateInstant(Instant instant) {
        this.instant = instant;
        return this;
    }
}
