package com.dss.msscbrewery.services.impl;

import com.dss.msscbrewery.services.CustomerService;
import com.dss.msscbrewery.web.domain.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return new CustomerDTO().builder().id(id).name("Diego").build();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return CustomerDTO.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID customerId, CustomerDTO customerDTO) {
        // todo
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting {}", customerId);
    }
}
