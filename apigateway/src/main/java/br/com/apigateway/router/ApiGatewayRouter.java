package br.com.apigateway.router;

import br.com.apigateway.filters.ResponseTimeHeaderFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApiGatewayRouter {

    private ResponseTimeHeaderFilter responseTimeHeaderFilter;

    public ApiGatewayRouter(ResponseTimeHeaderFilter responseTimeHeaderFilter) {
        this.responseTimeHeaderFilter = responseTimeHeaderFilter;
    }

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder){

        return routeLocatorBuilder.routes()
                .route(
                    route -> route.path("/fakepay/costumer/**")
                            .filters(filter -> filter.rewritePath("/fakepay/costumer/(?<segment>.*)", "/${segment}")
                                    .filter(responseTimeHeaderFilter))
                            .uri("lb://CUSTOMER")
                )
                .route(
                        route -> route.path("/fakepay/wallet/**")
                                .filters(filter -> filter.rewritePath("/fakepay/wallet/(?<segment>.*)", "/${segment}")
                                        .filter(responseTimeHeaderFilter))
                                .uri("lb://WALLET")
                )
                .build();

    }
}
