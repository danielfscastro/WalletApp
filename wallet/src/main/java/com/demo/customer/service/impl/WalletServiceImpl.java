package com.demo.customer.service.impl;

import com.demo.customer.dto.CustomerDto;
import com.demo.customer.dto.WalletDto;
import com.demo.customer.entity.Customer;
import com.demo.customer.entity.Wallet;
import com.demo.customer.exception.ResourceNotFoundException;
import com.demo.customer.exception.WalletAlreadyExistsException;
import com.demo.customer.mapper.CustomerMapper;
import com.demo.customer.mapper.WalletMapper;
import com.demo.customer.repository.CustomerRepository;
import com.demo.customer.repository.WalletRepository;
import com.demo.customer.service.IWalletService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private WalletRepository walletRepository;
    private CustomerRepository customerRepository;

    @Override
    public void create(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByDocument(customerDto.getDocument());
        if (customerOptional.isPresent()) {
            throw new WalletAlreadyExistsException("Wallet already registered with given document "
                    + customerDto.getDocument());
        }

        Customer savedCustomer = customerRepository.save(customer);
        Wallet savedWallet = walletRepository.save(createWallet(savedCustomer));
    }

    private Wallet createWallet(Customer customer) {
        Wallet wallet = new Wallet();
        wallet.setCustomerNumber(customer.getCustomerNumber());

        long randomWalletNumber = 1000000000L + new Random().nextLong(900000000);
        wallet.setWalletNumber(randomWalletNumber);

        return wallet;
    }

    @Override
    public CustomerDto fetchWallet(String document, String correlationId) {
        Customer customer = customerRepository.findByDocument(document).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "document", document)
        );

        Wallet wallet = walletRepository.findByCustomerNumber(customer.getCustomerNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "document", document)
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        WalletDto walletDto = WalletMapper.mapToWalletDto(wallet, new WalletDto());
        customerDto.setWallet(walletDto);

        return customerDto;
    }

    @Override
    public WalletDto fetchWalletAt(LocalDate localDate, String document) {
        return null;
    }
}
