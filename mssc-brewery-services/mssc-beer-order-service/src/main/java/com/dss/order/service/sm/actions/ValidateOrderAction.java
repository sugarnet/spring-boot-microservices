package com.dss.order.service.sm.actions;

import com.dss.brewery.model.events.ValidateOrderRequest;
import com.dss.order.service.config.JmsConfig;
import com.dss.order.service.domain.BeerOrder;
import com.dss.order.service.domain.BeerOrderEventEnum;
import com.dss.order.service.domain.BeerOrderStatusEnum;
import com.dss.order.service.repositories.BeerOrderRepository;
import com.dss.order.service.services.BeerOrderManagerImpl;
import com.dss.order.service.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {

        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_REQUEST_QUEUE, ValidateOrderRequest.builder().beerOrder(beerOrderMapper.beerOrderToDto(beerOrder)).build());

        log.debug("Send validation request to queue for order id {}", beerOrderId);

    }
}
