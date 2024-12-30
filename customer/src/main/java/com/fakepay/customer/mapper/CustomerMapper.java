package com.fakepay.customer.mapper;

import com.fakepay.customer.dto.CustomerDetailDto;
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

    public static CustomerDetailDto mapToCustomerDetailDto(Customer customer, CustomerDetailDto customerDetailDto) {
        customerDetailDto.setName(customer.getName());
        customerDetailDto.setEmail(customer.getEmail());
        customerDetailDto.setDocument(customer.getDocument());
        return customerDetailDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setDocument(customerDto.getDocument());
        return customer;
    }

    public static WalletMsgDto mapToWalletMsgDto(Customer customer) {
        return new WalletMsgDto(customer.getDocument());
    }
}
