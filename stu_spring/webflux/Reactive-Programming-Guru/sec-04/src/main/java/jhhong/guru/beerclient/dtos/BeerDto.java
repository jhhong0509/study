package jhhong.guru.beerclient.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    @Null
    private UUID id;

    @NotBlank
    private String beerName;

    @NotBlank
    private BeerStyle beerStyle;

    @NotBlank
    private String upc;

    private Integer quantityOnHand;

    private BigDecimal price;

    private OffsetDateTime createdDate;
}
