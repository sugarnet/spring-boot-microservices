package com.dss.order.service.sm.actions;

import com.dss.brewery.model.events.AllocationFailureEvent;
import com.dss.order.service.config.JmsConfig;
import com.dss.order.service.domain.BeerOrderEventEnum;
import com.dss.order.service.domain.BeerOrderStatusEnum;
import com.dss.order.service.services.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {

        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        if (Objects.nonNull(beerOrderId)) {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_FAILURE_QUEUE,
                    AllocationFailureEvent.builder().orderId(UUID.fromString(beerOrderId)).build());

            log.info("Compensating transaction... allocation failed for order id {}", beerOrderId);
        } else {
            log.info("Compensating transaction... Message didn't sent");
        }
    }
}
