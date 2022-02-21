package com.dss.order.service.services;

import com.dss.order.service.domain.BeerOrder;

public interface BeerOrderManager {
    BeerOrder saveNewOrder(BeerOrder beerOrder);
}
