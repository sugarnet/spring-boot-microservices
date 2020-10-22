package com.dss.msscbrewery.services.impl;

import com.dss.msscbrewery.services.CustomerService;
import com.dss.msscbrewery.web.domain.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return new CustomerDTO().builder().id(id).name("Diego").build();
    }
}
