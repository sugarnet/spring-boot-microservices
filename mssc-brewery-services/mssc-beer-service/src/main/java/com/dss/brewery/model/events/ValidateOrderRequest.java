package com.dss.brewery.model.events;

import com.dss.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ValidateOrderRequest implements Serializable {
    static final long serialVersionUID = 1303932321858888387L;

    private BeerOrderDto beerOrderDto;
}
