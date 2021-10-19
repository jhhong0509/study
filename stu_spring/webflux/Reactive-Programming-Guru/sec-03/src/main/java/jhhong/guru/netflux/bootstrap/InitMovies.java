package jhhong.guru.netflux.bootstrap;

import jhhong.guru.netflux.domain.Movie;
import jhhong.guru.netflux.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class InitMovies implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Override
    public void run(String... args) {
        movieRepository.deleteAll()
                .thenMany(Flux.just("movie1", "movie2", "movie3", "movie4", "movie5")
                        .map(title -> Movie.builder().title(title).build())
                        .flatMap(movieRepository::save)
                ).subscribe(null, null, () ->
                movieRepository.findAll().map(Movie::getId).subscribe(System.out::println));
    }
}
