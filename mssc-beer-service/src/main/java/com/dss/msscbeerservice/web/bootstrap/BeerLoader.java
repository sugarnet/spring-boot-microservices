package com.dss.msscbeerservice.web.bootstrap;

import com.dss.msscbeerservice.web.domain.Beer;
import com.dss.msscbeerservice.web.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createBeers();
    }

    private void createBeers() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("Super Red")
                    .beerStyle("IPA")
                    .upc(123456l)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal("12.99"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("The Rock")
                    .beerStyle("PALE_ALE")
                    .upc(123999l)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal("19.99"))
                    .build());
        }
    }
}
