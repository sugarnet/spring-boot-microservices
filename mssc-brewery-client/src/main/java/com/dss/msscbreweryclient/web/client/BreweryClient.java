package com.dss.msscbreweryclient.web.client;

import com.dss.msscbreweryclient.web.model.BeerDto;
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
public class BreweryClient {

    private static final String BEER_PATH_V1 = "/api/v1/beer/";
    private String apiHost;

    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID beerId) {
        return restTemplate.getForObject(apiHost + BEER_PATH_V1 + beerId.toString(), BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(apiHost + BEER_PATH_V1, beerDto);
    }

    public void updateBeer(UUID beerId, BeerDto beerDto) {
        log.info("UUID {}", beerId);
        log.info("DTO {}", beerDto);
        restTemplate.put(apiHost + BEER_PATH_V1 + beerId.toString(), beerDto);
    }

    public void deleteBeer(UUID beerId) {
        log.info("UUID {}", beerId);
        restTemplate.delete(apiHost + BEER_PATH_V1 + beerId.toString());
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
