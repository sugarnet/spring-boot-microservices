package com.dss.msscbreweryclient.web.client;

import com.dss.msscbreweryclient.web.model.CustomerDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class CustomerClient {

    private static final String API_PATH_V1 = "/api/v1/customer/";
    private String apiHost;

    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDTO getCustomerById(UUID id) {
        return restTemplate.getForObject(apiHost + API_PATH_V1 + id.toString(), CustomerDTO.class);
    }

    public URI saveNewCustomer(CustomerDTO customerDTO) {
        return restTemplate.postForLocation(apiHost + API_PATH_V1, customerDTO);
    }

    public void updateCustomer(UUID uuid, CustomerDTO customerDTO) {
        restTemplate.put(apiHost + API_PATH_V1 + uuid.toString(), customerDTO);
    }

    public void deleteCustomer(UUID uuid) {
        restTemplate.delete(apiHost + API_PATH_V1 + uuid);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
