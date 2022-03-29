package com.dss.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {
    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                // beer service
                .route(r -> r.path("/api/v1/beer*", "/api/v1/beer/*", "/api/v1/beer/upc/*")
                        .uri("http://localhost:8080"))
                // order service
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081"))
                // inventory service
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .uri("http://localhost:8082"))
                .build();
    }
}
