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
    @Column(name = "document", nullable = false)
    private String document;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    public void add(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void subtract(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}