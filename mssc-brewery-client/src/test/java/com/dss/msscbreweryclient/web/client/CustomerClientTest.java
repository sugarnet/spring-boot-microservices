package com.dss.msscbreweryclient.web.client;

import com.dss.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    private CustomerClient customerClient;

    @Test
    void getCustomerById() {
        CustomerDto customerById = customerClient.getCustomerById(UUID.randomUUID());
        assertNotNull(customerById);
    }

    @Test
    void saveNewCustomer() {
        URI alma = customerClient.saveNewCustomer(CustomerDto.builder().id(UUID.randomUUID()).name("Alma").build());
        assertNotNull(alma);
        System.out.println(alma);
    }

    @Test
    void updateCustomer() {
        UUID customerId = UUID.randomUUID();
        customerClient.updateCustomer(customerId, CustomerDto.builder().id(customerId).name("Valen").build());
    }

    @Test
    void deleteCustomer() {
        customerClient.deleteCustomer(UUID.randomUUID());
    }
}