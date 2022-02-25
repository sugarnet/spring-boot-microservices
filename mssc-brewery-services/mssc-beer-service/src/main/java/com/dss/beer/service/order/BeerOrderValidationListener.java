package com.dss.beer.service.order;

import com.dss.beer.config.JmsConfig;
import com.dss.beer.domain.Beer;
import com.dss.beer.repository.BeerRepository;
import com.dss.brewery.model.BeerOrderDto;
import com.dss.brewery.model.BeerOrderLineDto;
import com.dss.brewery.model.events.ValidateOrderRequest;
import com.dss.brewery.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerOrderValidationListener {
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_REQUEST_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest) {

        BeerOrderDto beerOrderDto = validateOrderRequest.getBeerOrderDto();

        Boolean flag = true;

        for (BeerOrderLineDto dto : beerOrderDto.getBeerOrderLines()) {
            Beer beer = beerRepository.findByUpc(dto.getUpc()).orElse(null);

            if (Objects.isNull(beer)) {
                flag = false;
                break;
            }
        }

        ValidateOrderResult validateOrderResult = ValidateOrderResult.builder().orderId(beerOrderDto.getId()).isValid(flag).build();

        log.debug("Beer Order Id {}", beerOrderDto.getId());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, validateOrderResult);
    }
}
