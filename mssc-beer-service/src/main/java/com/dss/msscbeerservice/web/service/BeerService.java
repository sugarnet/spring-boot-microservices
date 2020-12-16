package com.dss.msscbeerservice.web.service;

import com.dss.msscbeerservice.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {
    BeerDTO getById(UUID beerId);
    BeerDTO saveNewBeer(BeerDTO beerDTO);
    BeerDTO updateBeer(UUID beerId, BeerDTO beerDTO);
}
