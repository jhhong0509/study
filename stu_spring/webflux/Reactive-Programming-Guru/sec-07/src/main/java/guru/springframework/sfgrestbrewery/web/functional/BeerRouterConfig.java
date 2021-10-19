package guru.springframework.sfgrestbrewery.web.functional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class BeerRouterConfig {

    public static final String BEER_V2_URL = "/api/v2/beer";
    public static final String BEER_V2_URL_ID = "/api/v2/beer/{beerId}";
    public static final String BEER_V2_URL_UPC = "/api/v2/beerUpc/{beerUpc}";
    public static final String BEER_V2_UPC = "/api/v2/beerUpc";

    private final BeerHandlerV2 beerHandlerV2;

    @Bean
    public RouterFunction<ServerResponse> beerRoutesV2(){
        return route().GET(BEER_V2_URL_ID, accept(MediaType.APPLICATION_JSON), beerHandlerV2::getBeerById)
                .GET(BEER_V2_URL_UPC, accept(MediaType.APPLICATION_JSON), beerHandlerV2::getBeerByUpc)
                .POST(BEER_V2_URL, beerHandlerV2::saveBeer)
                .PUT(BEER_V2_URL_ID, accept(MediaType.APPLICATION_JSON), beerHandlerV2::updateBeer)
                .DELETE(BEER_V2_URL_ID, beerHandlerV2::deleteBeer)
                .build();
    }
}
