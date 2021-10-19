package guru.springframework.sfgrestbrewery.web.controller;

import guru.springframework.sfgrestbrewery.domain.Beer;
import guru.springframework.sfgrestbrewery.services.BeerService;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import guru.springframework.sfgrestbrewery.web.model.BeerPagedList;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jt on 2019-04-20.
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = { "application/json" }, path = "beer")
    public ResponseEntity<Mono<BeerPagedList>> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return ResponseEntity.ok(beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand));

    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<Mono<BeerDto>> getBeerById(@PathVariable("beerId") Integer beerId,
                                                     @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        return ResponseEntity.ok(beerService.getById(beerId, showInventoryOnHand)
                .defaultIfEmpty(BeerDto.builder().build())
                .doOnNext(beerDto -> {
                    if(beerDto.getId() == null) {
                        throw new NotFoundException();
                    }
                }));
    }

    @GetMapping("beerUpc/{upc}")
    public ResponseEntity<Mono<BeerDto>> getBeerByUpc(@PathVariable("upc") String upc){
        return ResponseEntity.ok(beerService.getByUpc(upc));
    }

    @PostMapping(path = "beer")
    public ResponseEntity<Void> saveNewBeer(@RequestBody @Validated BeerDto beerDto){

        AtomicInteger atomicInteger = new AtomicInteger();
        beerService.saveNewBeer(Mono.just(beerDto))
                .subscribe(savedBeerDto -> atomicInteger.set(savedBeerDto.getId()));

        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://api.springframework.guru/api/v1/beer/" + atomicInteger.get())
                        .build().toUri())
                .build();
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity<Void> updateBeerById(@PathVariable("beerId") Integer beerId, @RequestBody @Validated BeerDto beerDto){
//        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//
//        beerService.updateBeer(beerId, beerDto).subscribe(savedDto -> {
//            if (savedDto.getId() != null) {
//                atomicBoolean.set(true);
//            }
//        });

//        if(atomicBoolean.get()) {
//            return ResponseEntity.noContent().build();
//        } else {
            return ResponseEntity.notFound().build();
//        }
    }

    @ExceptionHandler
    ResponseEntity<Void> handleNotFound(NotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("beer/{beerId}")
    public ResponseEntity<Void> deleteBeerById(@PathVariable("beerId") Integer beerId) {
        beerService.deleteBeerById(beerId);

        return ResponseEntity.ok().build();
    }

}
