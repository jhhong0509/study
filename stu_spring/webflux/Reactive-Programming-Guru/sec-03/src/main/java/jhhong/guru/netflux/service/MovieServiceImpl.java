package jhhong.guru.netflux.service;

import jhhong.guru.netflux.domain.Movie;
import jhhong.guru.netflux.domain.MovieEvent;
import jhhong.guru.netflux.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Mono<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Flux<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Flux<MovieEvent> streamMovieEvent(String id) {
        return Flux.<MovieEvent>generate(movieEventFluxSink -> {
            movieEventFluxSink.next(MovieEvent.builder().movieId(id).movieDate(LocalDateTime.now()).build());
        }).delayElements(Duration.ofSeconds(3));
    }

}