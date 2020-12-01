package com.dss.msscbrewery.services;

import com.dss.msscbrewery.web.model.CustomerDTO;

import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void updateBeer(UUID customerId, CustomerDTO customerDTO);

    void deleteById(UUID customerId);
}
