package com.fakepay.wallet.entity;

import com.fakepay.wallet.constants.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_history_id", nullable = false)
    private Long transactionHistoryId;

    @Enumerated(EnumType.STRING)  // Armazena o nome da constante do enum
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "transaction_value", nullable = false)
    private BigDecimal transactionValue;

    @Column(name = "transaction_currency", nullable = false)
    private String transactionCurrency;

    @Column(name = "document_origin", nullable = false)
    private String documentOrigin;

    @Column(name = "document_destination", nullable = false)
    private String documentDestination;
}