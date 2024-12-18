package com.demo.customer.service.impl;

import com.demo.customer.dto.WalletDto;
import com.demo.customer.entity.Wallet;
import com.demo.customer.exception.ResourceNotFoundException;
import com.demo.customer.mapper.WalletMapper;
import com.demo.customer.repository.WalletRepository;
import com.demo.customer.service.IWalletService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private WalletRepository walletRepository;

    @Override
    public void create(WalletDto walletDto) {

        /**
         * TODO: create a client to validate if customer exists.
         */

        Wallet wallet = WalletMapper.mapToWallet(walletDto, new Wallet());

        Wallet savedWallet = walletRepository.save(wallet);
    }

    @Override
    public WalletDto fetchWallet(Long customerNumber, String correlationId) {
        Wallet wallet = walletRepository.findByCustomerNumber(customerNumber).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "customerNumber", String.valueOf(customerNumber))
        );

        return WalletMapper.mapToWalletDto(wallet, new WalletDto());
    }
}
