package com.demo.customer.service.client;

import com.demo.customer.dto.WalletDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletFallback implements WalletFeignClient{

    @Override
    public ResponseEntity<WalletDto> fetchWalletDetails(String correlationId, String document) {
        return null;
    }
}
