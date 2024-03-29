package com.dss.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

//@Profile("google")
//@Configuration
public class GoogleConfig {

    //@Bean
    public RouteLocator googleRouteConfig(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/search")
                        .filters(f -> f.rewritePath("/search2(?<segment>/?.*)", "/${segment}"))
                        .uri("https://www.google.com"))
                .build();
    }

}
