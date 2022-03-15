package com.dss.order.service.sm.actions;

import com.dss.brewery.model.events.AllocateOrderRequest;
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

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final JmsTemplate jmsTemplate;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {

        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        if (Objects.nonNull(beerOrderId)) {
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

            beerOrderOptional.ifPresentOrElse(beerOrder -> {
                jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
                        AllocateOrderRequest.builder().beerOrder(beerOrderMapper.beerOrderToDto(beerOrder)).build());
            }, () -> log.error("Beer Order Not Found"));

        }

        log.debug("Sent allocation request for order id {}", beerOrderId);

    }
}
