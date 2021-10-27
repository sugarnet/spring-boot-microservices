package com.dss.msscbrewery.service;

import com.dss.msscbrewery.web.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return BeerDTO.builder().id(UUID.randomUUID()).beerName("Andes").beerStyle("LAGER").build();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        log.info("DTO {}", beerDTO);
        return BeerDTO.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTO beerDTO) {
        log.info("DTO {}", beerDTO);
        log.info("UUID {}", beerId);
        //todo
    }

    @Override
    public void deleteById(UUID beerId) {
        log.info("Deleting a beer {}", beerId);
    }
}
