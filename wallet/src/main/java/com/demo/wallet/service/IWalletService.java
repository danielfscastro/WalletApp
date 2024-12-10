package com.demo.wallet.service;

import com.demo.wallet.dto.CustomerDto;
import com.demo.wallet.dto.WalletDto;

import java.time.LocalDate;

public interface IWalletService {
    public void create(CustomerDto customerDto);

    public CustomerDto fetchWallet(String document);

    public WalletDto fetchWalletAt(LocalDate localDate, String document);
}
