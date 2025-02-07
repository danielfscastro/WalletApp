package com.fakepay.apigateway.security;

import org.springframework.security.config.web.server.ServerHttpSecurity;

public final class GatewayAuthHttpRequestConfigurer {

    public static void configure(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange((requests) -> requests
                        .pathMatchers("/fakepay/customer/**", "/fakepay/wallet/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/fakepay/admin/**").hasRole("ADMIN")
                        .pathMatchers("/fakepay/user").authenticated()
                        .pathMatchers("/fakepay/contact", "/fakepay/home", "/fakepay/error", "/fakepay/register").permitAll()
                        .anyExchange().authenticated()
                        )
                        ;

    }
}
