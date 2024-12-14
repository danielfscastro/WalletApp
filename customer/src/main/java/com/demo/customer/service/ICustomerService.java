package com.demo.customer.service;

import com.demo.customer.dto.CustomerDto;

public interface ICustomerService {
    public void create(CustomerDto customerDto);

    public CustomerDto fetch(String document, String correlationId);
}
