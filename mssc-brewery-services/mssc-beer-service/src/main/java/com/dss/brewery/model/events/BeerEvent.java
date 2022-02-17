package com.dss.brewery.model.events;

import com.dss.brewery.model.BeerDto;
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
