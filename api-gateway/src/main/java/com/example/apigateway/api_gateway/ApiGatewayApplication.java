package com.example.apigateway.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
/**
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("usuarios", r -> r.path("/usuarios/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://MS-USUARIOS"))
                .route("tickets", r -> r.path("/v1/tickets/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://MS-TICKETS"))
                .build();
    }
    */
}
