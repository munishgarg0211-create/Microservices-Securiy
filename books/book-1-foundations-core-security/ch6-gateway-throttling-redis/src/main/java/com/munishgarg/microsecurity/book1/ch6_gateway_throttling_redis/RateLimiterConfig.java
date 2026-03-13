package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    @Primary
    public KeyResolver userKeyResolver() {
        // Resolve rate limit key by user query parameter (simulating a principal/API key)
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getQueryParams().getFirst("user"))
                .defaultIfEmpty("anonymous");
    }
}
