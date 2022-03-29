package com.dss.order.service.services;

import com.dss.brewery.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;


public interface CustomerService {

    CustomerPagedList listCustomers(Pageable pageable);
}
