package com.fakepay.apigateway.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class GatewayCorsFilter {

    /**
     * Creates a Spring WebFlux CorsWebFilter bean instance.
     * <p>
     * This CORS configuration is used by the Gateway to allow the UI to request
     * resources from the Gateway.
     * <p>
     * The configuration is set to allow CORS requests from
     * {@code http://localhost:4200}, which is the default URL that the UI uses
     * when running in development mode.
     * <p>
     * The configuration allows all types of HTTP methods, allows credentials,
     * and allows all headers.
     * <p>
     * The configuration also sets the maximum age of the CORS configuration to
     * 1 hour.
     * <p>
     * The exposed headers are set to allow the UI to access the Authorization
     * header.
     */
    @Bean
    public CorsWebFilter defaultCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);

    }
}
