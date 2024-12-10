package com.demo.wallet.service;

import com.demo.wallet.dto.CustomerBalanceAtDto;
import com.demo.wallet.dto.TransactionDto;
import com.demo.wallet.dto.TransferTransactionDto;

import java.time.LocalDate;

public interface ITransactionService {
    public boolean deposit(TransactionDto depositDto);

    public boolean withdraw(TransactionDto wallet);

    public CustomerBalanceAtDto fetchBalanceAt(String document, LocalDate date);

    boolean transfer(TransferTransactionDto transferTransactionDto);
}
