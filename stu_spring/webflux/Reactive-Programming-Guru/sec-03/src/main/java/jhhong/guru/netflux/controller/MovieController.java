package jhhong.guru.netflux.controller;

import jhhong.guru.netflux.domain.Movie;
import jhhong.guru.netflux.domain.MovieEvent;
import jhhong.guru.netflux.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/movie")
@RestController
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    Mono<Movie> getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    Flux<Movie> getAllMovie() {
        return movieService.getAllMovies();
    }

    @GetMapping(value = "/{id}/event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<MovieEvent> streamEvent(@PathVariable String id) {
        return movieService.streamMovieEvent(id);
    }
}
