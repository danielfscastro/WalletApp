package com.fakepay.customer.service.client;

import com.fakepay.customer.dto.WalletDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletFallback implements WalletFeignClient{

    @Override
    public ResponseEntity<WalletDto> fetchWalletDetails(String document) {
        return null;
    }
}
