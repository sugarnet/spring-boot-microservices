package com.dss.msscbrewery.services;

import com.dss.msscbrewery.web.domain.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return BeerDTO.builder()
                .id(beerId)
                .beerName("SuperRed")
                .beerStyle("Red")
                .build();
    }
}
