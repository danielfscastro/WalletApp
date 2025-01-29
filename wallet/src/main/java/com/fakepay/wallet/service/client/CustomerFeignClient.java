package com.fakepay.wallet.service.client;

import com.fakepay.wallet.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="customer", fallback = CustomerFallback.class)
public interface CustomerFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CustomerDto> fetchWalletDetails(@RequestParam Long customerNumber);
}
