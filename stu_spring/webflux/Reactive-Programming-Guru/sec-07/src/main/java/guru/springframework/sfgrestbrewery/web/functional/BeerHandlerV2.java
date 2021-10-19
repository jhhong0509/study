package guru.springframework.sfgrestbrewery.web.functional;

import ch.qos.logback.classic.spi.IThrowableProxy;
import guru.springframework.sfgrestbrewery.services.BeerService;
import guru.springframework.sfgrestbrewery.web.controller.NotFoundException;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerHandlerV2 {

    private final BeerService beerService;
    private final Validator validator;

    public Mono<ServerResponse> getBeerById(ServerRequest request){
        Integer beerId = Integer.valueOf(request.pathVariable("beerId"));
        Boolean showInventory = Boolean.valueOf(request.queryParam("showInventory").orElse("false"));

        return beerService.getById(beerId, showInventory)
                .flatMap(beerDto -> ServerResponse.ok().bodyValue(beerDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getBeerByUpc(ServerRequest request) {
        String upc = request.pathVariable("beerUpc");

        return beerService.getByUpc(upc)
                .flatMap(beerDto -> ServerResponse.ok().bodyValue(beerDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveBeer(ServerRequest request) {
        Mono<BeerDto> beerDtoMono = request.bodyToMono(BeerDto.class)
                .doOnNext(this::validate);

        return beerService.saveNewBeer(beerDtoMono)
                .flatMap(beerDto -> ServerResponse
                        .created(URI.create(BeerRouterConfig.BEER_V2_URL + "/" + beerDto.getId()))
                        .build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateBeer(ServerRequest request) {
        Integer beerId = Integer.valueOf(request.pathVariable("beerId"));

        Mono<BeerDto> beerDtoMono = request.bodyToMono(BeerDto.class)
                .doOnNext(this::validate);

        return beerService.updateBeer(beerId, beerDtoMono)
                .flatMap(beerDto -> ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteBeer(ServerRequest request) {
        Integer beerId = Integer.valueOf(request.pathVariable("beerId"));

        return beerService.reactiveDeleteBeer(beerId)
                .flatMap(unused -> ServerResponse.noContent().build())
                .onErrorResume(e -> e instanceof NotFoundException, e -> ServerResponse.notFound().build());
    }

    private void validate(BeerDto beerDto) {
        Errors errors = new BeanPropertyBindingResult(beerDto, "beerDto");
        validator.validate(beerDto, errors);

        if(errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}