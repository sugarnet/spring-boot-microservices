package com.dss.msscbrewery.web.controller;

import com.dss.msscbrewery.services.CustomerService;
import com.dss.msscbrewery.web.domain.BeerDTO;
import com.dss.msscbrewery.web.domain.CustomerDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        final CustomerDTO customer = customerService.saveCustomer(customerDTO);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer".concat(customer.getId().toString()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity updateBeer(@PathVariable UUID customerId, @RequestBody CustomerDTO customerDTO) {
        customerService.updateBeer(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable UUID customerId) {
        customerService.deleteById(customerId);
    }
}
