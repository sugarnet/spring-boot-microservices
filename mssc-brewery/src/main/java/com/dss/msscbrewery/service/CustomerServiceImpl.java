package com.dss.msscbrewery.service;

import com.dss.msscbrewery.web.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO getCustomerById(UUID customerId) {
        return CustomerDTO.builder().id(UUID.randomUUID()).name("Diego").build();
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        return CustomerDTO.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO, UUID customerId) {
        log.info("Updating a Customer");
    }

    @Override
    public void delete(UUID customerId) {
        log.info("Deleting a Customer");
    }
}
