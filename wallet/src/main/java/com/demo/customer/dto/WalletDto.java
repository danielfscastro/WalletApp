package com.demo.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(
        name = "Wallet",
        description = "Schema to hold wallet information"
)
public class WalletDto {

    @NotNull(message = "Customer Number cannot be null")
    @Positive(message = "Customer number value must be a positive number")
    @Schema(
            description = "Customer Number", example = "1234567890N"
    )
    private Long customerNumber;

    @NotNull(message = "Balance cannot be null")
    @Digits(integer = 18, fraction = 8, message = "Balance must be a number with up to 10 total digits and 2 decimal places")
    @Schema(
            description = "Wallet Balance", example = "1000.00"
    )
    private BigDecimal balance;
}