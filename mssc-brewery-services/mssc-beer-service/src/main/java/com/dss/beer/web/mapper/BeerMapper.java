package com.dss.beer.web.mapper;

import com.dss.beer.domain.Beer;
import com.dss.brewery.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto beerDto);
    BeerDto beerToBeerDto(Beer beer);
    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
