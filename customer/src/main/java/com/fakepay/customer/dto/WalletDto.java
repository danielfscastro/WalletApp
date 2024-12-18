package com.fakepay.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Wallet",
        description = "Schema to hold wallet information"
)
public class WalletDto {

    @NotNull(message = "Wallet Number cannot be null")
    @Digits(integer = 10, fraction = 0, message = "Wallet Number must be a 10-digit number")
    @Schema(
            description = "Wallet Number", example = "3454433243"
    )
    private Long walletNumber;

    @NotNull(message = "Balance cannot be null")
    @Digits(integer = 18, fraction = 8, message = "Balance must be a number with up to 10 total digits and 2 decimal places")
    @Schema(
            description = "Wallet Balance", example = "1000.00"
    )
    private BigDecimal balance;
}