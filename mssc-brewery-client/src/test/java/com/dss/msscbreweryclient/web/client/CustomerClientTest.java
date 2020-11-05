package com.dss.msscbreweryclient.web.client;

import com.dss.msscbreweryclient.web.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerClientTest {
    @Autowired
    private CustomerClient customerClient;

    @Test
    public void getCustomerById() {
        CustomerDTO customerDTO = customerClient.getCustomerById(UUID.randomUUID());

        assertNotNull(customerDTO);
    }

    @Test
    void saveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("New Customer").build();

        URI uri = customerClient.saveNewCustomer(customerDTO);

        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Old Customer").build();
        customerClient.updateCustomer(UUID.randomUUID(), customerDTO);
    }

    @Test
    void deleteCustomer() {
        customerClient.deleteCustomer(UUID.randomUUID());
    }
}