package com.dss.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfig {
    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                // beer service
                .route(r -> r.path("/api/v1/beer*", "/api/v1/beer/*", "/api/v1/beer/upc/*")
                        .uri("lb://beer-service"))
                // order service
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://order-service"))
                // inventory service
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")))
                        .uri("lb://inventory-service"))
                // inventory failover
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://inventory-failover"))
                .build();
    }
}
