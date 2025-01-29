package com.fakepay.customer.service;

import com.fakepay.customer.dto.CustomerDetailDto;
import com.fakepay.customer.dto.CustomerDto;

public interface ICustomerService {
    public void create(CustomerDto customerDto);

    public CustomerDetailDto fetch(String document);
}
