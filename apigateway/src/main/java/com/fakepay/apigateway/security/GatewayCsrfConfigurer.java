package com.fakepay.apigateway.security;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestHandler;
import org.springframework.security.web.server.csrf.XorServerCsrfTokenRequestAttributeHandler;

public final class GatewayCsrfConfigurer {

    public static void configure(ServerHttpSecurity http) throws Exception {
        CookieServerCsrfTokenRepository tokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
        XorServerCsrfTokenRequestAttributeHandler delegate = new XorServerCsrfTokenRequestAttributeHandler();
        // Use only the handle() method of XorServerCsrfTokenRequestAttributeHandler and the
        // default implementation of resolveCsrfTokenValue() from ServerCsrfTokenRequestHandler
        ServerCsrfTokenRequestHandler requestHandler = delegate::handle;
        http.csrf(csrfConfig -> csrfConfig
                            .csrfTokenRequestHandler(requestHandler)
                            .csrfTokenRepository(tokenRepository)
                );
    }
}
