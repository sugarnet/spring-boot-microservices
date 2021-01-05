package com.dss.msscbeerservice.web.mapper;

import com.dss.msscbeerservice.domain.Beer;
import com.dss.msscbeerservice.web.model.BeerDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);
    BeerDTO beerToBeerDTOWithInventory(Beer beer);

    Beer beerDTOtoBeer(BeerDTO beerDTO);
}
