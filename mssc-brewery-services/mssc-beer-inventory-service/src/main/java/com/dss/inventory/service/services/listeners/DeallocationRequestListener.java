package com.dss.inventory.service.services.listeners;

import com.dss.brewery.model.events.DeallocateOrderRequest;
import com.dss.inventory.service.config.JmsConfig;
import com.dss.inventory.service.services.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeallocationRequestListener {
    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest deallocateOrderRequest) {

            allocationService.deallocateOrder(deallocateOrderRequest.getBeerOrder());

    }
}
