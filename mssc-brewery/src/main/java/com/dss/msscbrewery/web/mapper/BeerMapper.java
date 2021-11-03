package com.dss.msscbrewery.web.mapper;

import com.dss.msscbrewery.domain.Beer;
import com.dss.msscbrewery.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
