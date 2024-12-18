package com.demo.customer.dto;

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
        name = "Deposit",
        description = "Schema to hold transfer information"
)
public class TransferTransactionDto {

    @NotNull(message = "Customer Number Origin cannot be null")
    @Positive(message = "Customer number value must be a positive number")
    @Schema(
            description = "Customer Number Origin", example = "1234567890N"
    )
    private Long customerNumberOrigin;

    @NotEmpty(message = "Document cannot be null or empty")
    @Size(max = 14, message = "Document must be at most 14 characters")
    @Schema(description = "Customer Document to withdraw", example = "12345678901234")
    private String documentOrigin;

    @NotNull(message = "Customer Number Destination cannot be null")
    @Positive(message = "Customer number value must be a positive number")
    @Schema(
            description = "Customer Number Destination", example = "1234567890N"
    )
    private Long customerNumberDestination;

    @NotEmpty(message = "Document cannot be null or empty")
    @Size(max = 14, message = "Document must be at most 14 characters")
    @Schema(description = "Customer Document to Deposit", example = "12345678901234")
    private String documentDestination;

    @NotNull(message = "Deposit value cannot be null")
    @Positive(message = "Deposit value must be a positive number")
    @Schema(description = "Deposit Value", example = "100.00")
    private BigDecimal transactionValue;
}