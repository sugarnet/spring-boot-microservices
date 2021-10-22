package com.dss.msscbrewery.service;

import com.dss.msscbrewery.web.model.CustomerDTO;

import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID customerId);

    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);

    void updateCustomer(CustomerDTO customerDTO, UUID customerId);

    void delete(UUID customerId);
}
