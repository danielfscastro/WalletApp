package com.demo.customer.service;

import com.demo.customer.dto.CustomerBalanceAtDto;
import com.demo.customer.dto.TransactionDto;
import com.demo.customer.dto.TransferTransactionDto;

import java.time.LocalDate;

public interface ITransactionService {
    public boolean deposit(TransactionDto depositDto);

    public boolean withdraw(TransactionDto wallet);

    public CustomerBalanceAtDto fetchBalanceAt(String document, LocalDate date);

    boolean transfer(TransferTransactionDto transferTransactionDto);
}
