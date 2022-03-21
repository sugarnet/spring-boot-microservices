package com.dss.brewery.model.events;

import com.dss.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeallocateOrderRequest {
    private BeerOrderDto beerOrder;
}
