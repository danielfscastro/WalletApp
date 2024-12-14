package com.demo.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "wallet_number", nullable = false)
    private Long walletNumber;
    @Column(name = "customer_number", nullable = false)
    private Long customerNumber;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
}