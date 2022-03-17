package com.dss.order.service.services.testscomponents;

import com.dss.brewery.model.events.ValidateOrderRequest;
import com.dss.brewery.model.events.ValidateOrderResult;
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
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_REQUEST_QUEUE)
    public void listen(Message<ValidateOrderRequest> message) {

        boolean isValid = true;
        ValidateOrderRequest validateOrderRequest = message.getPayload();

        if (Objects.nonNull(validateOrderRequest.getBeerOrder().getCustomerRef()) && validateOrderRequest.getBeerOrder().getCustomerRef().equals("fail-validation")) {
            isValid = false;
        }

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(isValid).orderId(validateOrderRequest.getBeerOrder().getId()).build());

    }
}
