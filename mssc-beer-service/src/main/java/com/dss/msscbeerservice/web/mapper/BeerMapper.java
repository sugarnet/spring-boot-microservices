package com.dss.msscbeerservice.web.mapper;

import com.dss.msscbeerservice.web.domain.Beer;
import com.dss.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto beerDto);
    BeerDto beerToBeerDto(Beer beer);
    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
