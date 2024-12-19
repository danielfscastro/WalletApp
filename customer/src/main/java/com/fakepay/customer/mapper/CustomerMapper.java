package com.fakepay.customer.mapper;

import com.fakepay.customer.dto.CustomerDto;
import com.fakepay.customer.dto.WalletMsgDto;
import com.fakepay.customer.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setDocument(customer.getDocument());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setDocument(customerDto.getDocument());
        return customer;
    }

    public static WalletMsgDto mapToWalletMsgDto(Customer customer) {
        return new WalletMsgDto(customer.getCustomerNumber());
    }
}
