package com.dss.msscbrewery.services.v2;

import com.dss.msscbrewery.web.domain.v2.BeerDTO;

import java.util.UUID;

public interface BeerServiceV2 {

    BeerDTO getBeerById(UUID beerId);

    BeerDTO saveBeer(BeerDTO beerDTO);

    void updateBeer(UUID beerId, BeerDTO beerDTO);

    void deleteById(UUID beerId);
}
