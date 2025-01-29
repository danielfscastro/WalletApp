package com.fakepay.customer.service.impl;

import com.fakepay.customer.dto.CustomerDetailDto;
import com.fakepay.customer.dto.CustomerDto;
import com.fakepay.customer.dto.WalletDto;
import com.fakepay.customer.entity.Customer;
import com.fakepay.customer.exception.ResourceNotFoundException;
import com.fakepay.customer.exception.CustomerAlreadyExistsException;
import com.fakepay.customer.mapper.CustomerMapper;
import com.fakepay.customer.repository.CustomerRepository;
import com.fakepay.customer.service.ICustomerService;
import com.fakepay.customer.service.IMessagingService;
import com.fakepay.customer.service.client.WalletFeignClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;
    private WalletFeignClient walletFeignClient;
    private IMessagingService messagingService;

    @Override
    public void create(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByDocument(customerDto.getDocument());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given document "
                    + customerDto.getDocument());
        }

        Customer savedCustomer = customerRepository.save(customer);
        messagingService.sendCommunication(CustomerMapper.mapToWalletMsgDto(savedCustomer));

    }

    @Override
    public CustomerDetailDto fetch(String document) {
        Customer customer = customerRepository.findByDocument(document).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "document", document)
        );

        CustomerDetailDto customerDetailDto = CustomerMapper.mapToCustomerDetailDto(customer, new CustomerDetailDto());

        ResponseEntity<WalletDto> walletDtoResponseEntity = walletFeignClient.fetchWalletDetails(customer.getDocument());
        if(walletDtoResponseEntity != null) {
            customerDetailDto.setWallet(walletDtoResponseEntity.getBody());
        }

        return customerDetailDto;
    }
}
