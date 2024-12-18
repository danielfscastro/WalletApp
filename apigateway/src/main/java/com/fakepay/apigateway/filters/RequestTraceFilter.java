package com.fakepay.apigateway.filters;

import com.fasterxml.uuid.Generators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        String correlationId = filterUtility.getCorrelationId(requestHeaders);

        if (correlationId != null) {
            logger.debug("wallet-correlation-id found in RequestTraceFilter: {}", correlationId);
        } else {
            correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationId);
            logger.debug("wallet-correlation-id generated in RequestTraceFilter: {}", correlationId);
        }
        return chain.filter(exchange);
    }

    private String generateCorrelationId() {
        return Generators.timeBasedGenerator().generate().toString(); // Retain for random UUID generation
    }
}