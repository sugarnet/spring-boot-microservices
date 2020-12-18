package com.dss.msscbrewery.web.mapper;

import com.dss.msscbrewery.domain.Beer;
import com.dss.msscbrewery.web.model.v2.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDTOToBeer(BeerDTO beerDTO);
}
