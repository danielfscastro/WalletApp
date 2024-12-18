package com.demo.customer.service;

import com.demo.customer.dto.CustomerDto;
import com.demo.customer.dto.WalletDto;

import java.time.LocalDate;

public interface IWalletService {
    public void create(WalletDto wallet);

    public WalletDto fetchWallet(Long customerNumber, String correlationId);

}
