package com.dss.msscbrewery.services.v2;

import com.dss.msscbrewery.web.domain.v2.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO getBeerById(UUID beerId);

    BeerDTO saveBeer(BeerDTO beerDTO);

    void updateBeer(UUID beerId, BeerDTO beerDTO);

    void deleteById(UUID beerId);
}
