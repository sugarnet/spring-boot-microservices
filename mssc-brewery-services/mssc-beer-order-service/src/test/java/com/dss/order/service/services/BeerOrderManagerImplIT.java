package com.dss.order.service.services;

import com.dss.order.service.domain.BeerOrder;
import com.dss.order.service.domain.BeerOrderLine;
import com.dss.order.service.domain.BeerOrderStatusEnum;
import com.dss.order.service.domain.Customer;
import com.dss.order.service.repositories.BeerOrderRepository;
import com.dss.order.service.repositories.CustomerRepository;
import com.dss.order.service.services.beer.BeerServiceRestTemplateImpl;
import com.dss.order.service.services.beer.model.BeerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(WireMockExtension.class)
@SpringBootTest
public class BeerOrderManagerImplIT {


    @Autowired
    BeerOrderManager beerOrderManager;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    ObjectMapper objectMapper;

    Customer testCustomer;

    UUID uuid = UUID.randomUUID();

    @TestConfiguration
    static class RestTemplateBuilderProvider {

        @Bean(destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            WireMockServer server = with(wireMockConfig().port(8083));
            server.start();
            return server;
        }
    }

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.save(Customer.builder().customerName("Customer Test").build());
    }

    @Test
    void testNewToAllocated() throws JsonProcessingException, InterruptedException {

        BeerDto beerDto = BeerDto.builder().id(uuid).upc("1234").build();

        wireMockServer.stubFor(get(BeerServiceRestTemplateImpl.BEER_SERVICE_PATH + "1234")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));

        BeerOrder beerOrder = createBeerOrder();

        BeerOrder savedBeerOrder = beerOrderManager.saveNewOrder(beerOrder);

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            assertEquals(BeerOrderStatusEnum.ALLOCATED, foundOrder.getOrderStatus());
        });

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();
            BeerOrderLine line = foundOrder.getBeerOrderLines().iterator().next();
            assertEquals(line.getOrderQuantity(), line.getQuantityAllocated());
        });

        BeerOrder savedBeerOrder2 = beerOrderRepository.findById(savedBeerOrder.getId()).get();

        assertNotNull(savedBeerOrder2);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder2.getOrderStatus());
        savedBeerOrder2.getBeerOrderLines().forEach(line -> {
            assertEquals(line.getOrderQuantity(), line.getQuantityAllocated());
        });
    }

    @Test
    void testFailedValidation() throws JsonProcessingException {
        BeerDto beerDto = BeerDto.builder().id(uuid).upc("1234").build();

        wireMockServer.stubFor(get(BeerServiceRestTemplateImpl.BEER_SERVICE_PATH + "1234")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));

        BeerOrder beerOrder = createBeerOrder();
        beerOrder.setCustomerRef("fail-validation");

        BeerOrder savedBeerOrder = beerOrderManager.saveNewOrder(beerOrder);

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            assertEquals(BeerOrderStatusEnum.VALIDATION_EXCEPTION, foundOrder.getOrderStatus());
        });
    }

    @Test
    void testFailedAllocation() throws JsonProcessingException {
        BeerDto beerDto = BeerDto.builder().id(uuid).upc("1234").build();

        wireMockServer.stubFor(get(BeerServiceRestTemplateImpl.BEER_SERVICE_PATH + "1234")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));

        BeerOrder beerOrder = createBeerOrder();
        beerOrder.setCustomerRef("allocation-error");

        BeerOrder savedBeerOrder = beerOrderManager.saveNewOrder(beerOrder);

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            assertEquals(BeerOrderStatusEnum.ALLOCATION_EXCEPTION, foundOrder.getOrderStatus());
        });
    }

    @Test
    void testPartialAllocation() throws JsonProcessingException {
        BeerDto beerDto = BeerDto.builder().id(uuid).upc("1234").build();

        wireMockServer.stubFor(get(BeerServiceRestTemplateImpl.BEER_SERVICE_PATH + "1234")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));

        BeerOrder beerOrder = createBeerOrder();
        beerOrder.setCustomerRef("pending-inventory");

        BeerOrder savedBeerOrder = beerOrderManager.saveNewOrder(beerOrder);

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            assertEquals(BeerOrderStatusEnum.PENDING_INVENTORY, foundOrder.getOrderStatus());
        });
    }

    @Test
    void testNewToPickedUp() throws JsonProcessingException {
        BeerDto beerDto = BeerDto.builder().id(uuid).upc("1234").build();

        wireMockServer.stubFor(get(BeerServiceRestTemplateImpl.BEER_SERVICE_PATH + "1234")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));

        BeerOrder beerOrder = createBeerOrder();

        BeerOrder savedBeerOrder = beerOrderManager.saveNewOrder(beerOrder);

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            assertEquals(BeerOrderStatusEnum.ALLOCATED, foundOrder.getOrderStatus());
        });

        beerOrderManager.beerOrderPickedUp(savedBeerOrder.getId());

        await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            assertEquals(BeerOrderStatusEnum.PICKED_UP, foundOrder.getOrderStatus());
        });

        BeerOrder pickedUpOrder = beerOrderRepository.findById(beerOrder.getId()).get();

        assertEquals(BeerOrderStatusEnum.PICKED_UP, pickedUpOrder.getOrderStatus());
    }

    public BeerOrder createBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder().customer(testCustomer).build();

        Set<BeerOrderLine> beerOrderLines = new HashSet<>();
        beerOrderLines.add(BeerOrderLine.builder().beerOrder(beerOrder).beerId(uuid).upc("1234").orderQuantity(1).build());

        beerOrder.setBeerOrderLines(beerOrderLines);

        return beerOrder;
    }
}
