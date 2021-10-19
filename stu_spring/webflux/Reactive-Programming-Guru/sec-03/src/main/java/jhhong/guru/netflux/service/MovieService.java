package jhhong.guru.netflux.service;

import jhhong.guru.netflux.domain.Movie;
import jhhong.guru.netflux.domain.MovieEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    Mono<Movie> getMovieById(String id);

    Flux<Movie> getAllMovies();

    Flux<MovieEvent> streamMovieEvent(String id);

}
