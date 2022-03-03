package com.dss.order.service.services.beer.listeners;

import com.dss.brewery.model.events.ValidateOrderResult;
import com.dss.order.service.config.JmsConfig;
import com.dss.order.service.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESULT_QUEUE)
    public void listen(ValidateOrderResult validateOrderResult) {

        log.debug("Beer Order Validated Id {}", validateOrderResult.getOrderId());

        beerOrderManager.processValidationResult(validateOrderResult.getOrderId(), validateOrderResult.getIsValid());
    }
}
