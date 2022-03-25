package com.dss.inventory.service.services.listeners;

import com.dss.brewery.model.BeerOrderDto;
import com.dss.brewery.model.events.AllocateOrderRequest;
import com.dss.brewery.model.events.AllocateOrderResult;
import com.dss.inventory.service.config.JmsConfig;
import com.dss.inventory.service.services.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllocationRequestListener {
    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest allocateOrderRequest) {

        BeerOrderDto beerOrderDto = allocateOrderRequest.getBeerOrder();

        log.debug("Beer Order Id Allocation {}", beerOrderDto.getId());

        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.beerOrder(beerOrderDto);

        try {
            Boolean allocationResult = allocationService.allocateOrder(beerOrderDto);

            builder.pendingInventory(true);
            if (allocationResult) {
                builder.pendingInventory(false);
            }

            builder.allocationError(false);
        } catch (Exception e) {
            builder.allocationError(true);
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE, builder.build());
    }
}
