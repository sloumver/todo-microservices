package com.todoapp.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class GatewayConfig {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    @Bean
    @Order(-1)
    public GlobalFilter requestLoggingFilter() {
        return (exchange, chain) -> {
            logger.info("Incoming request: {} {}", 
                exchange.getRequest().getMethod(), 
                exchange.getRequest().getURI().getPath());
            
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Outgoing response: {}", 
                    exchange.getResponse().getStatusCode());
            }));
        };
    }
}