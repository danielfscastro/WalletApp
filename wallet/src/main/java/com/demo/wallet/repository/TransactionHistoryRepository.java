package com.demo.wallet.repository;

import com.demo.wallet.entity.Customer;
import com.demo.wallet.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    Optional<TransactionHistory> findByCustomerNumber(Long customerNumber);
    List<TransactionHistory> findByDocumentOriginAndCreatedAt(String document, LocalDate createdAt);

    @Query("SELECT t FROM TransactionHistory t WHERE t.documentOrigin = :document AND t.createdAt >= :startDate AND t.createdAt < :endDate")
    List<TransactionHistory> findTransactionsByDocumentAndDate(@Param("document") String document,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);
}
