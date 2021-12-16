package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.services.beer.model.BeerDto;

import java.util.Optional;

public interface BeerService {

    Optional<BeerDto> getBeerByUpc(String upc);
}
