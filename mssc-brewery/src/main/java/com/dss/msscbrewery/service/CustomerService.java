package com.dss.msscbrewery.service;

import com.dss.msscbrewery.web.model.CustomerDTO;

import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID customerId);
}
