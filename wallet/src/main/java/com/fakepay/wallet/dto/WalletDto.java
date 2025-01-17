package com.fakepay.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Wallet",
        description = "Schema to hold wallet information"
)
public class WalletDto {

    @NotEmpty(message = "Document cannot be null or empty")
    @Size(max = 14, message = "Document must be at most 14 characters")
    @Schema(description = "Customer Document", example = "12345678901234")
    private String document;

    @NotNull(message = "Balance cannot be null")
    @Digits(integer = 18, fraction = 8, message = "Balance must be a number with up to 10 total digits and 2 decimal places")
    @Schema(
            description = "Wallet Balance", example = "1000.00"
    )
    private BigDecimal balance;
}