package com.demo.customer.service;

import com.demo.customer.dto.CustomerDto;
import com.demo.customer.dto.WalletDto;

import java.time.LocalDate;

public interface IWalletService {
    public void create(CustomerDto customerDto);

    public CustomerDto fetchWallet(String document);

    public WalletDto fetchWalletAt(LocalDate localDate, String document);
}
