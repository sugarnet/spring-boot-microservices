package com.dss.beer.events;

import com.dss.brewery.model.BeerDto;
import com.dss.brewery.model.events.BeerEvent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
