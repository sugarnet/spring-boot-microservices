package com.dss.msscbeerservice.web.mapper;

import com.dss.msscbeerservice.web.domain.Beer;
import com.dss.msscbeerservice.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDTOtoBeer(BeerDTO beerDTO);
}
