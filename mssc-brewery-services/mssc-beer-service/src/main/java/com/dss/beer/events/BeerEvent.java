package com.dss.beer.events;

import com.dss.beer.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {
    static final long serialVersionUID = -6909459281320832545L;

    private BeerDto beerDto;
}
