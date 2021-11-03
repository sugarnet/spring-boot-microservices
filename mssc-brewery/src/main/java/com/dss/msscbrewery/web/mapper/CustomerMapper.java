package com.dss.msscbrewery.web.mapper;

import com.dss.msscbrewery.domain.Customer;
import com.dss.msscbrewery.web.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
