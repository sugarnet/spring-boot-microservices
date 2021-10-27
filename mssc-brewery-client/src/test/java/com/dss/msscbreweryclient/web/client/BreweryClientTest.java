package com.dss.msscbreweryclient.web.client;

import com.dss.msscbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    private BreweryClient breweryClient;

    @Test
    void getBeerById() {
        BeerDto beerById = breweryClient.getBeerById(UUID.randomUUID());
        assertNotNull(beerById);
    }

    @Test
    void saveNewBeer() {
        URI uri = breweryClient.saveNewBeer(BeerDto.builder().id(UUID.randomUUID()).beerName("Patagonia").beerStyle("IPA").upc(123l).build());
        assertNotNull(uri);
        System.out.println(uri.toString());
    }

    @Test
    void updateBeer() {
        UUID beerId = UUID.randomUUID();
        breweryClient.updateBeer(beerId, BeerDto.builder().id(beerId).beerName("Patagonia").beerStyle("IPA").upc(123l).build());
    }

    @Test
    void deleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }
}