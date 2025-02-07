package com.fakepay.apigateway.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayCsrfFilter {

    @Bean
    WebFilter csrfCookieWebFilter() {
        return (exchange, chain) -> {
            Mono<CsrfToken> csrfToken = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
            return csrfToken.doOnSuccess(token -> {
                /* Ensures the token is subscribed to. */
                System.out.println("csrf token -> " + token.getHeaderName() + " value -> " + token.getToken());
            }).then(chain.filter(exchange));
        };
    }
}
