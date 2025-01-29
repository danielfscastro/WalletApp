package com.fakepay.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Schema(
        name = "CustomerDetail",
        description = "Schema to hold customer detail information"
)
public class CustomerDetailDto extends CustomerDto{
    private WalletDto wallet;
}