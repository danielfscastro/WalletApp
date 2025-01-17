package com.fakepay.wallet.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDto {

    @NotNull(message = "Transaction Type cannot be null")
    @Pattern(regexp = "deposit|withdraw|transfer", message = "Transaction Type must be 'deposit', 'withdraw', or 'transfer'")
    private String transactionType;

    @NotNull(message = "Transaction Value cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Transaction Value must be greater than 0")
    @Digits(integer = 18, fraction = 8, message = "Transaction Value must have at most 18 digits before the decimal and 8 digits after the decimal")
    private BigDecimal transactionValue;

    @NotNull(message = "Transaction Currency cannot be null")
    @Size(min = 3, max = 10, message = "Transaction Currency must be between 3 and 10 characters")
    private String transactionCurrency;

    @NotNull(message = "Document Origin cannot be null")
    @Size(min = 11, max = 14, message = "Document Origin must be between 1 and 20 characters")
    private String documentOrigin;

    @NotNull(message = "Document Destination cannot be null")
    @Size(min = 11, max = 14, message = "Document Destination must be between 1 and 20 characters")
    private String documentDestination;

    @NotNull(message = "Created At cannot be null")
    private LocalDate createdAt;
}