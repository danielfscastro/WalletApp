package com.fakepay.apigateway.router;

import com.fakepay.apigateway.filters.ResponseTimeHeaderFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApiGatewayRouter {

    private ResponseTimeHeaderFilter responseTimeHeaderFilter;

    private RedisRateLimiter redisRateLimiter;

    private KeyResolver userKeyResolver;

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder){

        return routeLocatorBuilder.routes()
                .route(
                        route -> route.path("/fakepay/customer/**")
                                .filters(filter -> filter.rewritePath("/fakepay/customer/(?<segment>.*)", "/${segment}")
                                        .filter(responseTimeHeaderFilter)
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter).setKeyResolver(userKeyResolver))
                                        .circuitBreaker(config -> config.setName("customerCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport")))
                                .uri("lb://CUSTOMER")
                )
                .route(
                        route -> route.path("/fakepay/wallet/**")
                                .filters(filter -> filter.rewritePath("/fakepay/wallet/(?<segment>.*)", "/${segment}")
                                        .filter(responseTimeHeaderFilter)
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter).setKeyResolver(userKeyResolver))
                                        .circuitBreaker(config -> config.setName("walletCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport")))
                                .uri("lb://WALLET")
                )
                .build();

    }
}
