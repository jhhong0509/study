package jhhong.guru.beerclient.client;

import jhhong.guru.beerclient.dtos.BeerDto;
import jhhong.guru.beerclient.dtos.BeerListResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerClient {
    Mono<BeerDto> getBeerById(UUID id, Boolean showInventoryOnHand);

    Mono<BeerDto> getBeerByUPC(String upc);

    Mono<BeerListResponse> listBeers(Integer pageNumber, Integer pageSize, String beerName,
                                     String beerStyle, Boolean showInventoryOnHand);

    Mono<ResponseEntity<Void>> createBeer(BeerDto beerDto);

    Mono<ResponseEntity<Void>> updateBeer(UUID id, BeerDto beerDto);

    Mono<ResponseEntity<Void>> deleteBeer(UUID id);
}
