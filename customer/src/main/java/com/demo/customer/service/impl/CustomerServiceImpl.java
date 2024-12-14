package com.demo.customer.service.impl;

import com.demo.customer.dto.CustomerDto;
import com.demo.customer.dto.WalletDto;
import com.demo.customer.entity.Customer;
import com.demo.customer.exception.ResourceNotFoundException;
import com.demo.customer.exception.CustomerAlreadyExistsException;
import com.demo.customer.mapper.CustomerMapper;
import com.demo.customer.repository.CustomerRepository;
import com.demo.customer.service.ICustomerService;
import com.demo.customer.service.client.WalletFeignClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;
    private WalletFeignClient walletFeignClient;

    @Override
    public void create(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByDocument(customerDto.getDocument());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given document "
                    + customerDto.getDocument());
        }

        Customer savedCustomer = customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetch(String document, String correlationId) {
        Customer customer = customerRepository.findByDocument(document).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "document", document)
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

        ResponseEntity<WalletDto> walletDtoResponseEntity = walletFeignClient.fetchWalletDetails(document,correlationId);
        if(walletDtoResponseEntity != null) {
            customerDto.setWallet(walletDtoResponseEntity.getBody());
        }

        return customerDto;
    }
}
