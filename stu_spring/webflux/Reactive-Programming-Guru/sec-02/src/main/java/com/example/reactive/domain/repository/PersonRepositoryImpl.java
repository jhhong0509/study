package com.example.reactive.domain.repository;

import com.example.reactive.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {
    Person michael = Person.builder()
            .firstName("firstName")
            .lastName("lastName")
            .id(1)
            .build();

    Person fiona = Person.builder()
            .firstName("fiona")
            .lastName("hong")
            .id(2)
            .build();

    Person sam = Person.builder()
            .firstName("sam")
            .lastName("hwang")
            .id(3)
            .build();

    @Override
    public Mono<Person> findById(Integer id) {
        return findAll().filter(person -> person.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, fiona, sam);
    }
}
