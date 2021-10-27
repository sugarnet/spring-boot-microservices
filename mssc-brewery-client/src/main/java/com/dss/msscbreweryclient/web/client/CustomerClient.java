package com.dss.msscbreweryclient.web.client;

import com.dss.msscbreweryclient.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Slf4j
@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class CustomerClient {

    private static final String CUSTOMER_PATH_V1 = "/api/v1/customer/";
    private String apiHost;

    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID customerId) {
        return restTemplate.getForObject(apiHost + CUSTOMER_PATH_V1 + customerId.toString(), CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(apiHost + CUSTOMER_PATH_V1, customerDto);
    }

    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        log.info("UUID {}", customerId);
        log.info("DTO {}", customerDto);
        restTemplate.put(apiHost + CUSTOMER_PATH_V1 + customerId.toString(), customerDto);
    }

    public void deleteCustomer(UUID customerId) {
        log.info("UUID {}", customerId);
        restTemplate.delete(apiHost + CUSTOMER_PATH_V1 + customerId.toString());
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
