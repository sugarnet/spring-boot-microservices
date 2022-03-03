package com.dss.order.service.services;

import com.dss.order.service.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {
    BeerOrder saveNewOrder(BeerOrder beerOrder);
    void processValidationResult(UUID orderId, boolean isValid);
}
