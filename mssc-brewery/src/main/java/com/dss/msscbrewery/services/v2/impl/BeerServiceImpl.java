package com.dss.msscbrewery.services.v2.impl;

import com.dss.msscbrewery.services.v2.BeerService;
import com.dss.msscbrewery.web.domain.v2.BeerDTO;
import com.dss.msscbrewery.web.domain.v2.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return BeerDTO.builder()
                .id(beerId)
                .beerName("SuperRed")
                .beerStyle(BeerStyle.IPA)
                .build();
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return BeerDTO.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTO beerDTO) {
        // todo
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("Deleting id {}", beerId);
    }
}
