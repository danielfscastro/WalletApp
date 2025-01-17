package com.fakepay.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Customer",
        description = "Schema to hold customer balance at"
)
public class CustomerBalanceAtDto {

    @NotNull(message = "Document Origin cannot be null")
    @Size(min = 11, max = 14, message = "Document Origin must be between 1 and 20 characters")
    private String document;

    @Digits(integer = 18, fraction = 8, message = "Balance must be a number with up to 10 total digits and 2 decimal places")
    @Schema(
            description = "Wallet Balance", example = "1000.00"
    )
    private BigDecimal balance;

    @NotNull(message = "Created At cannot be null")
    private LocalDate balanceAt;
}