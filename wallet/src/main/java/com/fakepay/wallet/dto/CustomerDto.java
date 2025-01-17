package com.fakepay.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Customer",
        description = "Schema to hold customer information"
)
public class CustomerDto {

    @NotEmpty(message = "Customer name cannot be null or empty")
    @Size(max = 100, message = "Customer name must be at most 100 characters")
    @Schema(description = "Customer Name", example = "Daniel Castro")
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Size(max = 100, message = "Email must be at most 100 characters")
    @Schema(description = "Customer Email", example = "example@example.com")
    private String email;

    @NotEmpty(message = "Document cannot be null or empty")
    @Size(max = 14, message = "Document must be at most 14 characters")
    @Schema(description = "Customer Document", example = "12345678901234")
    private String document;

    private WalletDto wallet;  // Assuming this is a valid DTO representing the wallet information
}