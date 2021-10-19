package com.example.reactive.domain.repository;

import com.example.reactive.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new PersonRepositoryImpl();
    }

    @Test
    void findByIdBlock() {
        Mono<Person> personMono = personRepository.findById(1);

        Person person = personMono.block();
        System.out.println(person.toString());

        do {
            System.out.println("dsfsadf");
        } while (true);
    }

    @Test
    void findByIdSubscribe() {
        Mono<Person> personMono = personRepository.findById(1);

        personMono
                .flatMap(person -> Mono.just(person.getFirstName()))
                .subscribe(System.out::println);
    }

    @Test
    void findByIdMap() {
        Mono<Person> personMono = personRepository.findById(1);

        personMono.map(person -> {
            System.out.println(person.toString());
            return person.getId();
        });
    }

    @Test
    void findByIdMapWithSubscribe() {
        Mono<Person> personMono = personRepository.findById(1);

        personMono.map(person -> {
            System.out.println(person.toString());
            return person.getId();
        }).subscribe(System.out::println);

    }

    @Test
    void findAllBlock() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();
        System.out.println(person.toString());
    }

    @Test
    void findAllSubscribe() {
        Flux<Person> personFlux = personRepository.findAll();

        StepVerifier.create(personFlux).expectNextCount(3).verifyComplete();

        personFlux
                .flatMap(person -> Mono.just(person.getFirstName()))
                .subscribe(System.out::println);
    }

    @Test
    void fluxToMonoList() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> personListMono = personFlux.collectList();

        personListMono.subscribe(list ->
                list.forEach(person -> System.out.println(person.toString()))
        );
    }

    @Test
    void testFindPersonById() {
        Flux<Person> personFlux = personRepository.findAll();

        final int id = 3;
        Mono<Person> personMono = personFlux
                .filter(person -> person.getId() == id)
                .next();

        personMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void testFindPersonByIdNo() {
        Flux<Person> personFlux = personRepository.findAll();

        final int id = 1289;
        Mono<Person> personMono = personFlux
                .filter(person -> person.getId() == id)
                .next();

        personMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void testFindPersonByIdNotFoundWithException() {
        Flux<Person> personFlux = personRepository.findAll();

        final int id = 1289;
        Mono<Person> personMono = personFlux
                .filter(person -> person.getId() == id)
                .single();

        personMono.doOnError(throwable -> System.out.println("error occured"))
                .onErrorReturn(Person.builder().build())
                .subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void homeworkFindByIdToNotHardCode() {
        Mono<Person> personMono = personRepository.findById(1);

        StepVerifier.create(personMono)
                .expectNextCount(1).verifyComplete();

        personMono.flatMap(person -> Mono.just(person.toString()))
                .subscribe(System.out::println);
    }

    @Test
    void homeworkFindByIdNotFound() {
        Mono<Person> personMono = personRepository.findById(19129);

        StepVerifier.create(personMono)
                .expectNextCount(0).verifyComplete();

        personMono.flatMap(person -> Mono.just(person.toString()))
                .subscribe(System.out::println);

    }

}