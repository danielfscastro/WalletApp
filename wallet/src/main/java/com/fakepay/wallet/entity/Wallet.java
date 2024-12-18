package com.fakepay.wallet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletNumber;
    @Column(name = "customer_number", nullable = false)
    private Long customerNumber;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
}