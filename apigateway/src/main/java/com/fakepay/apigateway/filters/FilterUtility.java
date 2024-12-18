package com.fakepay.apigateway.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "wallet-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        List<String> correlationIdList = requestHeaders.get(CORRELATION_ID);
        return (correlationIdList != null && !correlationIdList.isEmpty()) ? correlationIdList.get(0) : null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        // Mutate and set header more efficiently
        return exchange.mutate()
                .request(exchange.getRequest().mutate().header(name, value).build())
                .build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }
}