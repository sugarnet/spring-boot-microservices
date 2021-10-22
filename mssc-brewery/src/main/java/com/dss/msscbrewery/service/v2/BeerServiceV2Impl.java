package com.dss.msscbrewery.service.v2;

import com.dss.msscbrewery.web.model.v2.BeerDTOV2;
import com.dss.msscbrewery.web.model.v2.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceV2Impl implements BeerServiceV2 {
    @Override
    public BeerDTOV2 getBeerById(UUID beerId) {
        return BeerDTOV2.builder().id(UUID.randomUUID()).beerName("Andes").beerStyle(BeerStyleEnum.LAGER).build();
    }

    @Override
    public BeerDTOV2 saveNewBeer(BeerDTOV2 beerDTO) {
        return BeerDTOV2.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTOV2 beerDTO) {
        log.debug("updating a beer");
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("deleting a beer");
    }
}
