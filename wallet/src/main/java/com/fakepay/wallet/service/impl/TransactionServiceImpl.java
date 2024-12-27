package com.fakepay.wallet.service.impl;

import com.fakepay.wallet.constants.TransactionType;
import com.fakepay.wallet.constants.WalletConstants;
import com.fakepay.wallet.dto.CustomerBalanceAtDto;
import com.fakepay.wallet.dto.TransactionDto;
import com.fakepay.wallet.dto.TransferTransactionDto;
import com.fakepay.wallet.entity.TransactionHistory;
import com.fakepay.wallet.entity.Wallet;
import com.fakepay.wallet.exception.ResourceNotFoundException;
import com.fakepay.wallet.exception.TransactionHistoryNotFoundException;
import com.fakepay.wallet.repository.TransactionHistoryRepository;
import com.fakepay.wallet.repository.WalletRepository;
import com.fakepay.wallet.service.ITransactionService;
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
        Wallet wallet = walletRepository.findByDocument(transactionDto.getDocument()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "document", transactionDto.getDocument())
        );

        wallet.add(transactionDto.getTransactionValue());
        Wallet savedWallet = walletRepository.save(wallet);

        transactionHistoryRepository.save(buildTransationHistoryDTO(transactionDto, TransactionType.DEPOSIT));

        return true;
    }

    @Override
    public boolean withdraw(TransactionDto transactionDto) {
        Wallet wallet = walletRepository.findByDocument(transactionDto.getDocument()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "document", transactionDto.getDocument())
        );

        wallet.subtract(transactionDto.getTransactionValue());
        Wallet savedWallet = walletRepository.save(wallet);

        transactionHistoryRepository.save(buildTransationHistoryDTO(transactionDto, TransactionType.WITHDRAW));

        return true;
    }

    @Override
    public CustomerBalanceAtDto fetchBalanceAt(String document, LocalDate date) {
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

        Wallet walletOrigin = walletRepository.findByDocument(transferTransactionDto.getDocumentOrigin()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet Origin", "documentOrigin", transferTransactionDto.getDocumentOrigin())
        );

        Wallet walletDestination = walletRepository.findByDocument(transferTransactionDto.getDocumentDestination()).orElseThrow(
                () -> new ResourceNotFoundException("Wallet Destination", "documentDestination", transferTransactionDto.getDocumentDestination())
        );

        walletOrigin.subtract(transferTransactionDto.getTransactionValue());
        walletDestination.add(transferTransactionDto.getTransactionValue());

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
