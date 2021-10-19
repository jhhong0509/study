package jhhong.guru.beerclient.client;

import jhhong.guru.beerclient.config.WebClientConfig;
import jhhong.guru.beerclient.dtos.BeerDto;
import jhhong.guru.beerclient.dtos.BeerListResponse;
import jhhong.guru.beerclient.dtos.BeerStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BeerClientImplTest {

    BeerClient beerClient;

    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }

    @Test
    void listBeers() {
        Mono<BeerListResponse> beerListResponseMono = beerClient.listBeers(null, null,
                null, null, null);

        BeerListResponse response = beerListResponseMono.block();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getContent().size() > 0);
    }

    @Test
    void listBeersPageSize() {
        Mono<BeerListResponse> beerListResponseMono = beerClient.listBeers(1, 10,
                null, null, null);

        BeerListResponse response = beerListResponseMono.block();
        Assertions.assertNotNull(response);
        assertEquals(response.getContent().size(), 10);
    }

    @Test
    void listBeersPageSizeNoRecords() {
        Mono<BeerListResponse> beerListResponseMono = beerClient.listBeers(10, 20,
                null, null, null);

        BeerListResponse response = beerListResponseMono.block();
        Assertions.assertNotNull(response);
        assertEquals(response.getContent().size(), 0);
    }

    @Test
    void getBeerById() {
        Mono<BeerListResponse> beerListResponseMono = beerClient.listBeers(null, null, null, null, null);

        UUID id = beerListResponseMono
                .map(beerDtos -> beerDtos.getContent())
                .map(beerDtos -> beerDtos.get(0))
                .map(BeerDto::getId)
                .block();

        BeerDto beerDto = beerClient.getBeerById(id, false).block();
        Assertions.assertNotNull(beerDto.getQuantityOnHand());
        Assertions.assertEquals(beerDto.getId(), id);
    }

    @Test
    void getBeerByIdFunctionalStyle() throws InterruptedException {

        AtomicReference<String> beerName = new AtomicReference<>();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        beerClient.listBeers(null, null, null, null, null)
                .map(beerDtos -> beerDtos.getContent().get(0))
                .map(BeerDto::getId)
                .map(id -> beerClient.getBeerById(id, false))
                .flatMap(mono -> mono)
                .subscribe(beerDto -> {
                    beerName.set(beerDto.getBeerName());
                    countDownLatch.countDown();
                });

        countDownLatch.await();

        Assertions.assertEquals(beerName.get(), "Mango Bobs");
    }

    @Test
    void getBeerByIdShowInventoryTrue() {
        Mono<BeerListResponse> beerListResponseMono = beerClient.listBeers(null, null, null, null, null);

        UUID id = beerListResponseMono
                .map(beerDtos -> beerDtos.getContent())
                .map(beerDtos -> beerDtos.get(0))
                .map(BeerDto::getId)
                .block();

        BeerDto beerDto = beerClient.getBeerById(id, true).block();
        Assertions.assertNotNull(beerDto.getQuantityOnHand());
        Assertions.assertEquals(beerDto.getId(), id);
    }

    @Test
    void getBeerByUPC() {
        Mono<BeerListResponse> beerListResponseMono = beerClient.listBeers(null, null, null, null, null);

        String upc = beerListResponseMono
                .map(beerDtos -> beerDtos.getContent())
                .map(beerDtos -> beerDtos.get(1))
                .map(BeerDto::getUpc)
                .block();

        BeerDto beerDto = beerClient.getBeerByUPC(upc).block();
        Assertions.assertNotNull(beerDto.getQuantityOnHand());
        Assertions.assertEquals(beerDto.getUpc(), upc);
    }

    @Test
    void createBeer() {
        BeerDto beerDto = BeerDto.builder()
                .beerName("beerName!!!")
                .beerStyle(BeerStyle.IPA)
                .upc("2345678987654")
                .price(new BigDecimal("10.99"))
                .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.createBeer(beerDto);
        ResponseEntity responseEntity = responseEntityMono.block();

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    void updateBeer() {
        BeerDto beerDto = BeerDto.builder()
                .beerName("beerName!!!")
                .beerStyle(BeerStyle.IPA)
                .upc("2345678987654")
                .price(new BigDecimal("10.99"))
                .build();

        BeerListResponse response = beerClient.listBeers(null, null, null, null, null).block();
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.updateBeer(response.getContent().get(0).getId(), beerDto);
        ResponseEntity responseEntity = responseEntityMono.block();

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteBeer() {
        BeerListResponse response = beerClient.listBeers(null, null, null, null, null).block();
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(response.getContent().get(0).getId());
        ResponseEntity responseEntity = responseEntityMono.block();

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteBeerByIdNotFound() {
        Assertions.assertThrows(WebClientResponseException.class, () ->
                beerClient.deleteBeer(UUID.randomUUID()).block());
    }

    @Test
    void deleteBeerByIdNotFound2() {
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(UUID.randomUUID());

        ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable -> {
            if(throwable instanceof WebClientResponseException) {
                WebClientResponseException exception = (WebClientResponseException) throwable;
                return Mono.just(ResponseEntity.status(exception.getStatusCode()).build());
            } else {
                throw new RuntimeException(throwable);
            }
        }).block();

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}