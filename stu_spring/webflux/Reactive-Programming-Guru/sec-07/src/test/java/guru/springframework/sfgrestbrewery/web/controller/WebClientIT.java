package guru.springframework.sfgrestbrewery.web.controller;

import guru.springframework.sfgrestbrewery.bootstrap.BeerLoader;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import guru.springframework.sfgrestbrewery.web.model.BeerPagedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by jt on 3/7/21.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebClientIT {

    public static final String BASE_URL = "http://localhost:8080";

    WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Test
    void testListBeers() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Mono<BeerPagedList> beerPagedListMono = webClient.get().uri("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(BeerPagedList.class);

        beerPagedListMono.publishOn(Schedulers.parallel()).subscribe(beerPagedList -> {

            beerPagedList.getContent().forEach(beerDto -> System.out.println(beerDto.toString()));

            countDownLatch.countDown();
        });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void testListBeersPageSize5() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Mono<BeerPagedList> beerPagedListMono = webClient.get().uri(uriBuilder -> {
            return uriBuilder.path("/api/v1/beer").queryParam("pageSize", "5").build();
        })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(BeerPagedList.class);

        beerPagedListMono.publishOn(Schedulers.parallel()).subscribe(beerPagedList -> {

            beerPagedList.getContent().forEach(beerDto -> System.out.println(beerDto.toString()));

            countDownLatch.countDown();
        });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void testListBeersByName() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Mono<BeerPagedList> beerPagedListMono = webClient.get().uri(uriBuilder -> {
            return uriBuilder.path("/api/v1/beer").queryParam("beerName", "Mango Bobs").build();
        })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(BeerPagedList.class);

        beerPagedListMono.publishOn(Schedulers.parallel()).subscribe(beerPagedList -> {

            beerPagedList.getContent().forEach(beerDto -> System.out.println(beerDto.toString()));

            countDownLatch.countDown();
        });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void getBeerByUPC() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Mono<BeerDto> beerDtoMono = webClient.get().uri("api/v1/beerUpc/" + BeerLoader.BEER_2_UPC)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(BeerDto.class);

        beerDtoMono.subscribe(beer -> {
            Assertions.assertNotNull(beer);
            Assertions.assertNotNull(beer.getBeerName());

            countDownLatch.countDown();
        });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void getBeerById() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Mono<BeerDto> beerDtoMono = webClient.get().uri("api/v1/beer/1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(BeerDto.class);

        beerDtoMono.subscribe(beer -> {
            Assertions.assertNotNull(beer);
            Assertions.assertNotNull(beer.getBeerName());

            countDownLatch.countDown();
        });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void testSaveBeer() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        BeerDto beerDto = BeerDto.builder()
                .beerName("JTs Beer")
                .upc("1233455")
                .beerStyle("PALE_ALE")
                .price(new BigDecimal("8.99"))
                .build();

        Mono<ResponseEntity<Void>> beerResponseMono = webClient.post()
                .uri("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(beerDto))
                .retrieve()
                .toBodilessEntity();

        beerResponseMono.publishOn(Schedulers.parallel())
                .subscribe(voidResponseEntity -> {
                    Assertions.assertTrue(voidResponseEntity.getStatusCode().is2xxSuccessful());
                    countDownLatch.countDown();
                });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void testSaveBeerBadRequest() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        BeerDto beerDto = BeerDto.builder()
                .price(new BigDecimal("8.99"))
                .build();

        Mono<ResponseEntity<Void>> beerResponseMono = webClient.post()
                .uri("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(beerDto))
                .retrieve()
                .toBodilessEntity();

        beerResponseMono.publishOn(Schedulers.parallel())
                .doOnError(throwable -> countDownLatch.countDown())
                .subscribe();

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(),0);
    }

    @Test
    void testUpdateBeer() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        webClient.get()
                .uri("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerPagedList.class)
                .publishOn(Schedulers.single())
                .subscribe(beerDtos -> {
                    countDownLatch.countDown();

                    BeerDto beerDto = beerDtos.getContent().get(0);     // 0번째를 가져오는건 일반적으로 하면 안되지만 우리는 30개의 데이터가 보장되기 때문에 해도 된다.
                    BeerDto updatedPayload = BeerDto.builder()
                            .beerName("JTsUPDATE")
                            .beerStyle(beerDto.getBeerStyle())
                            .upc(beerDto.getUpc())
                            .price(beerDto.getPrice())
                            .build();

                    webClient.put()
                            .uri("/api/v1/beer/" + beerDto.getId())
                            .body(BodyInserters.fromValue(updatedPayload))
                            .retrieve()
                            .toBodilessEntity()
                            .flatMap(voidResponseEntity -> {
                                countDownLatch.countDown();
                                return webClient.get()
                                        .uri("/api/v1/beer/" + beerDto.getId())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .retrieve()
                                        .bodyToMono(BeerDto.class);
                            }).subscribe(savedDto -> {
                                Assertions.assertEquals("JTsUPDATE", savedDto.getBeerName());
                                countDownLatch.countDown();
                            });
                });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void testUpdateBeerNotFound() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        BeerDto updatePayload = BeerDto.builder().beerName("JTsUpdate")
                .beerStyle("PALE_ALE")
                .upc("12345667")
                .price(new BigDecimal("9.99"))
                .build();

        webClient.put().uri("/api/v1/beer/" + 200 )
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatePayload))
                .retrieve().toBodilessEntity()
                .subscribe(responseEntity -> {}, throwable -> {
                    if (throwable.getClass().getName().equals("org.springframework.web.reactive.function.client.WebClientResponseException$NotFound")){
                        WebClientResponseException ex = (WebClientResponseException) throwable;

                        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                            countDownLatch.countDown();
                        }
                    }
                });

        countDownLatch.countDown();

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    void testDeleteBeer() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        webClient.get().uri("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerPagedList.class)
                .publishOn(Schedulers.single())
                .subscribe(pagedList -> {
                    countDownLatch.countDown();

                    BeerDto beerDto = pagedList.getContent().get(0);

                    webClient.delete().uri("/api/v1/beer/" + beerDto.getId() )
                            .retrieve().toBodilessEntity()
                            .flatMap(responseEntity -> {
                                countDownLatch.countDown();

                                return webClient.get().uri("/api/v1/beer/" + beerDto.getId())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .retrieve().bodyToMono(BeerDto.class);
                            }) .subscribe(savedDto -> {

                    }, throwable -> {
                        countDownLatch.countDown();
                    });
                });

        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(countDownLatch.getCount(), 0);
    }

}