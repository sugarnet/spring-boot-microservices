package com.dss.order.service.services.beer;

import com.dss.order.service.services.beer.model.BeerDto;

import java.util.Optional;

public interface BeerService {

    Optional<BeerDto> getBeerByUpc(String upc);
}
