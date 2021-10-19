package guru.springframework.sfgrestbrewery.bootstrap;

import guru.springframework.sfgrestbrewery.domain.Beer;
import guru.springframework.sfgrestbrewery.repositories.BeerRepository;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by jt on 3/7/21.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "9122089364369";
    public static final String BEER_3_UPC = "0083783375213";
    public static final String BEER_4_UPC = "4666337557578";
    public static final String BEER_5_UPC = "8380495518610";
    public static final String BEER_6_UPC = "5677465691934";
    public static final String BEER_7_UPC = "5463533082885";
    public static final String BEER_8_UPC = "5339741428398";
    public static final String BEER_9_UPC = "1726923962766";
    public static final String BEER_10_UPC = "8484957731774";
    public static final String BEER_11_UPC = "6266328524787";
    public static final String BEER_12_UPC = "7490217802727";
    public static final String BEER_13_UPC = "8579613295827";
    public static final String BEER_14_UPC = "2318301340601";
    public static final String BEER_15_UPC = "9401790633828";
    public static final String BEER_16_UPC = "4813896316225";
    public static final String BEER_17_UPC = "3431272499891";
    public static final String BEER_18_UPC = "2380867498485";
    public static final String BEER_19_UPC = "4323950503848";
    public static final String BEER_20_UPC = "4006016803570";
    public static final String BEER_21_UPC = "9883012356263";
    public static final String BEER_22_UPC = "0583668718888";
    public static final String BEER_23_UPC = "9006801347604";
    public static final String BEER_24_UPC = "0610275742736";
    public static final String BEER_25_UPC = "6504219363283";
    public static final String BEER_26_UPC = "7245173761003";
    public static final String BEER_27_UPC = "0326984155094";
    public static final String BEER_28_UPC = "1350188843012";
    public static final String BEER_29_UPC = "0986442492927";
    public static final String BEER_30_UPC = "8670687641074";


    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private synchronized void loadBeerObjects() {
        log.debug("Loading initial data. Count is: {}", beerRepository.count().block() );

        if (beerRepository.count().block() == 0) {

            Random random = new Random();

            beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.ALE)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("No Hammers On The Bar")
                    .beerStyle(BeerStyleEnum.WHEAT)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Blessed")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_4_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Adjunct Trail")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_5_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Very GGGreenn")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_6_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Double Barrel Hunahpu's")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_7_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Very Hazy")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_8_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("SR-71")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_9_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Pliny the Younger")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_10_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Blessed")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_11_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("King Krush")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_12_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("PBS Porter")
                    .beerStyle(BeerStyleEnum.PORTER)
                    .upc(BEER_13_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Pinball Porter")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_14_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Golden Budda")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_15_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Grand Central Red")
                    .beerStyle(BeerStyleEnum.LAGER)
                    .upc(BEER_16_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Pac-Man")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_17_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Ro Sham Bo")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_18_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Summer Wheatly")
                    .beerStyle(BeerStyleEnum.WHEAT)
                    .upc(BEER_19_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Java Jill")
                    .beerStyle(BeerStyleEnum.LAGER)
                    .upc(BEER_20_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Bike Trail Pale")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .upc(BEER_21_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("N.Z.P")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_22_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Stawberry Blond")
                    .beerStyle(BeerStyleEnum.WHEAT)
                    .upc(BEER_23_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Loco")
                    .beerStyle(BeerStyleEnum.PORTER)
                    .upc(BEER_24_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Spocktoberfest")
                    .beerStyle(BeerStyleEnum.STOUT)
                    .upc(BEER_25_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Beach Blond Ale")
                    .beerStyle(BeerStyleEnum.ALE)
                    .upc(BEER_26_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Bimini Twist IPA")
                    .beerStyle(BeerStyleEnum.IPA)
                    .upc(BEER_27_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Rod Bender Red Ale")
                    .beerStyle(BeerStyleEnum.ALE)
                    .upc(BEER_28_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("Floating Dock")
                    .beerStyle(BeerStyleEnum.SAISON)
                    .upc(BEER_29_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            beerRepository.save(Beer.builder()
                    .beerName("El Hefe")
                    .beerStyle(BeerStyleEnum.WHEAT)
                    .upc(BEER_30_UPC)
                    .price(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .quantityOnHand(random.nextInt(5000))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build()).block();

            log.debug("Beer Records loaded: {}", beerRepository.count().block());
        }
    }
}