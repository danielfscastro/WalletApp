package com.fakepay.customer.dto;

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
        name = "CustomerDetail",
        description = "Schema to hold customer detail information"
)
public class CustomerDetailDto extends CustomerDto{
    private WalletDto wallet;
}