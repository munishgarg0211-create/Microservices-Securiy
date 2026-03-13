package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Demonstrates perimeter security via Gateway Throttling.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the secure gateway throttling demo.
     * In a real system, the 'RedisRateLimiter' filter in Spring Cloud Gateway
     * would enforce limits before the request reaches this endpoint.
     */
    @GetMapping
    public Mono<Map<String, Object>> getDemo() {
        return demoService.demo();
    }
}
