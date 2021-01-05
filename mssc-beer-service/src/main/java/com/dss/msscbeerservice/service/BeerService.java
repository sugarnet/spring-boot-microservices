package com.dss.msscbeerservice.service;

import com.dss.msscbeerservice.web.model.BeerDTO;
import com.dss.msscbeerservice.web.model.BeerPagedList;
import com.dss.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDTO getById(UUID beerId, Boolean showInventoryOnHand);
    BeerDTO saveNewBeer(BeerDTO beerDTO);
    BeerDTO updateBeer(UUID beerId, BeerDTO beerDTO);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);
}
