package com.fakepay.wallet.service.client;

import com.fakepay.wallet.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerFallback implements CustomerFeignClient {

    @Override
    public ResponseEntity<CustomerDto> fetchWalletDetails(Long customerNumber) {
        return null;
    }
}
