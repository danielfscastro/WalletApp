package com.fakepay.wallet.service;

import com.fakepay.wallet.dto.CustomerBalanceAtDto;
import com.fakepay.wallet.dto.TransactionDto;
import com.fakepay.wallet.dto.TransferTransactionDto;

import java.time.LocalDate;

public interface ITransactionService {
    public boolean deposit(TransactionDto depositDto);

    public boolean withdraw(TransactionDto wallet);

    public CustomerBalanceAtDto fetchBalanceAt(String document, LocalDate date);

    boolean transfer(TransferTransactionDto transferTransactionDto);
}
