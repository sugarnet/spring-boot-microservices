package com.dss.beer.events;

import com.dss.beer.web.model.BeerDto;
import com.dss.common.events.BeerEvent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
