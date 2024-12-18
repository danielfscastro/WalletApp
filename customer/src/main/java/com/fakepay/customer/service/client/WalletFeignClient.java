package com.fakepay.customer.service.client;

import com.fakepay.customer.dto.WalletDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="wallet", fallback = WalletFallback.class)
public interface WalletFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<WalletDto> fetchWalletDetails(@RequestHeader("wallet-correlation-id") String correlationId,
                                                        @RequestParam String document);
}
