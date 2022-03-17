package com.dss.order.service.services.testscomponents;

import com.dss.brewery.model.events.AllocateOrderRequest;
import com.dss.brewery.model.events.AllocateOrderResult;
import com.dss.order.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message<AllocateOrderRequest> message) {

        boolean pendingInventory = false;
        boolean allocationError = false;

        AllocateOrderRequest allocateOrderRequest = message.getPayload();

        if (Objects.nonNull(allocateOrderRequest.getBeerOrder().getCustomerRef()) && allocateOrderRequest.getBeerOrder().getCustomerRef().equals("pending-inventory")) {
            pendingInventory = true;
        }
        if (Objects.nonNull(allocateOrderRequest.getBeerOrder().getCustomerRef()) && allocateOrderRequest.getBeerOrder().getCustomerRef().equals("allocation-error")) {
            allocationError = true;
        }

        boolean finalPendingInventory = pendingInventory;
        allocateOrderRequest.getBeerOrder().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if (finalPendingInventory) {

                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {

                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE,
                AllocateOrderResult.builder().beerOrderDto(allocateOrderRequest.getBeerOrder()).pendingInventory(pendingInventory).allocationError(allocationError).build());

    }
}
