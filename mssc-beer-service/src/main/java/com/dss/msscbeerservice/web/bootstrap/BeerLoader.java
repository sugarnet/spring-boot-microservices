package com.dss.msscbeerservice.web.bootstrap;

import com.dss.msscbeerservice.web.domain.Beer;
import com.dss.msscbeerservice.web.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeers();
    }

    private void loadBeers() {
        if (beerRepository.count() == 0) {
            List<Beer> beers = Arrays.asList(
                    Beer.builder()
                            .beerName("Andes")
                            .beerStyle("PORTER")
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .upc(10000001l)
                            .price(new BigDecimal("150.00"))
                            .build(),
                    Beer.builder()
                            .beerName("Quilmes")
                            .beerStyle("LAGER")
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .upc(10000002l)
                            .price(new BigDecimal("160.00"))
                            .build());

            beerRepository.saveAll(beers);
        }

    }
}
