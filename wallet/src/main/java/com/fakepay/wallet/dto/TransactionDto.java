package com.fakepay.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Transaction",
        description = "Schema to hold transaction information"
)
public class TransactionDto {

    @NotEmpty(message = "Document cannot be null or empty")
    @Size(max = 14, message = "Document must be at most 14 characters")
    @Schema(description = "Customer Document", example = "12345678901234")
    private String document;

    @NotNull(message = "Deposit value cannot be null")
    @Positive(message = "Deposit value must be a positive number")
    @Schema(description = "Deposit Value", example = "100.00")
    private BigDecimal transactionValue;
}