package com.dss.order.service.services;

import com.dss.order.service.domain.BeerOrder;
import com.dss.order.service.domain.BeerOrderLine;
import com.dss.order.service.domain.BeerOrderStatusEnum;
import com.dss.order.service.domain.Customer;
import com.dss.order.service.repositories.BeerOrderRepository;
import com.dss.order.service.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BeerOrderManagerImplIT {


    @Autowired
    BeerOrderManager beerOrderManager;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    Customer testCustomer;

    UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.save(Customer.builder().customerName("Customer Test").build());
    }

    @Test
    void testNewToAllocated() {
        BeerOrder beerOrder = createBeerOrder();

        BeerOrder savedBeerOrder = beerOrderManager.saveNewOrder(beerOrder);

        assertNotNull(savedBeerOrder);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder.getOrderStatus());
    }

    public BeerOrder createBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder().customer(testCustomer).build();

        Set<BeerOrderLine> beerOrderLines = new HashSet<>();
        beerOrderLines.add(BeerOrderLine.builder().beerOrder(beerOrder).beerId(uuid).orderQuantity(1).build());

        beerOrder.setBeerOrderLines(beerOrderLines);

        return beerOrder;
    }
}
