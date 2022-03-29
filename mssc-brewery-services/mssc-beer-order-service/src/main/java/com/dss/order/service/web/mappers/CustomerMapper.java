package com.dss.order.service.web.mappers;

import com.dss.brewery.model.CustomerDto;
import com.dss.order.service.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerDto customerDto);
}
