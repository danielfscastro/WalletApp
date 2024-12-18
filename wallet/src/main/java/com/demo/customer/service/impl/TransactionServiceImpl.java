package com.demo.customer.service.impl;

import com.demo.customer.constants.TransactionType;
import com.demo.customer.constants.WalletConstants;
import com.demo.customer.dto.CustomerBalanceAtDto;
import com.demo.customer.dto.TransactionDto;
import com.demo.customer.dto.TransferTransactionDto;
import com.demo.customer.entity.TransactionHistory;
import com.demo.customer.entity.Wallet;
import com.demo.customer.exception.ResourceNotFoundException;
import com.demo.customer.exception.TransactionHistoryNotFoundException;
import com.demo.customer.repository.TransactionHistoryRepository;
import com.demo.customer.repository.WalletRepository;
import com.demo.customer.service.ITransactionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private WalletRepository walletRepository;
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public boolean deposit(TransactionDto transactionDto) {
        Wallet wallet = walletRepository.findByCustomerNumber(transactionDto.getCustomerNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "document", transactionDto.getDocument())
        );

        BigDecimal savedBalance = wallet.getBalance().add(transactionDto.getTransactionValue());
        wallet.setBalance(savedBalance);
        Wallet savedWallet = walletRepository.save(wallet);

        transactionHistoryRepository.save(buildTransationHistoryDTO(transactionDto, TransactionType.DEPOSIT));

        return true;
    }

    @Override
    public boolean withdraw(TransactionDto transactionDto) {
        Wallet wallet = walletRepository.findByCustomerNumber(transactionDto.getCustomerNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "document", transactionDto.getDocument())
        );

        BigDecimal savedBalance = wallet.getBalance().subtract(transactionDto.getTransactionValue());
        wallet.setBalance(savedBalance);
        Wallet savedWallet = walletRepository.save(wallet);

        transactionHistoryRepository.save(buildTransationHistoryDTO(transactionDto, TransactionType.WITHDRAW));

        return true;
    }

    @Override
    public CustomerBalanceAtDto fetchBalanceAt(String document, LocalDate date) {
        /**
         * TODO:// validate if customer exists
         */

        CustomerBalanceAtDto customerBalanceAtDto = new CustomerBalanceAtDto();
        customerBalanceAtDto.setDocument(document);
        customerBalanceAtDto.setBalanceAt(date);

        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.plusDays(1).atStartOfDay();
        List<TransactionHistory> transactions = transactionHistoryRepository.findTransactionsByDocumentAndDate(document,startDate,endDate);

        if(transactions.isEmpty()){
            Map<String, String> fields = new HashMap<String,String>();
            fields.put("document", document);
            fields.put("date", String.valueOf(startDate));
            throw new TransactionHistoryNotFoundException("Transaction History", fields );
        }

        BigDecimal balance = calculateBalance(transactions, date);
        customerBalanceAtDto.setBalance(balance);

        return customerBalanceAtDto;
    }

    @Override
    public boolean transfer(TransferTransactionDto transferTransactionDto) {

        Wallet walletOrigin = walletRepository.findByCustomerNumber(transferTransactionDto.getCustomerNumberOrigin()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet Origin", "documentOrigin", transferTransactionDto.getDocumentOrigin())
        );

        Wallet walletDestination = walletRepository.findByCustomerNumber(transferTransactionDto.getCustomerNumberDestination()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet Destination", "documentDestination", transferTransactionDto.getDocumentDestination())
        );

        BigDecimal savedBalanceOrigin = walletOrigin.getBalance().subtract(transferTransactionDto.getTransactionValue());
        walletOrigin.setBalance(savedBalanceOrigin);

        BigDecimal savedBalanceDestination = walletDestination.getBalance().add(transferTransactionDto.getTransactionValue());
        walletDestination.setBalance(savedBalanceDestination);

        Wallet savedWalletOrigin = walletRepository.save(walletOrigin);
        Wallet savedWalletDestination = walletRepository.save(walletDestination);

        transactionHistoryRepository.save(buildTransationHistoryDTO(transferTransactionDto, TransactionType.WITHDRAW));
        transactionHistoryRepository.save(buildTransationHistoryDTO(transferTransactionDto, TransactionType.DEPOSIT));

        return true;
    }

    private TransactionHistory buildTransationHistoryDTO(TransactionDto transactionDto,
                                                         TransactionType transactionType){

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionCurrency(WalletConstants.CURRENCY_BRL);
        transactionHistory.setTransactionValue(transactionDto.getTransactionValue());
        transactionHistory.setCustomerNumber(transactionDto.getCustomerNumber());
        transactionHistory.setDocumentOrigin(transactionDto.getDocument());
        transactionHistory.setDocumentDestination(transactionDto.getDocument());
        transactionHistory.setTransactionType(transactionType);

        return transactionHistory;
    }

    private TransactionHistory buildTransationHistoryDTO(TransferTransactionDto transferTransactionDto,
                                                         TransactionType transactionType){

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionCurrency(WalletConstants.CURRENCY_BRL);
        transactionHistory.setTransactionValue(transferTransactionDto.getTransactionValue());
        transactionHistory.setCustomerNumber(transferTransactionDto.getCustomerNumberOrigin());
        transactionHistory.setDocumentOrigin(transferTransactionDto.getDocumentOrigin());
        transactionHistory.setDocumentDestination(transferTransactionDto.getDocumentDestination());
        transactionHistory.setTransactionType(transactionType);

        return transactionHistory;
    }

    private BigDecimal calculateBalance(List<TransactionHistory> transactions, LocalDate date) {
        return transactions.stream()
                .filter(transaction -> !transaction.getCreatedAt().toLocalDate().isAfter(date)) // Inclui transações até a data
                .map(transaction -> {
                    // Adiciona o valor da transação dependendo do tipo
                    BigDecimal transactionValue = transaction.getTransactionValue();
                    return transaction.getTransactionType() == TransactionType.DEPOSIT
                            ? transactionValue
                            : transactionValue.negate(); // Para WITHDRAW, subtrai o valor
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma todos os valores
    }
}
