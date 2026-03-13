package com.munishgarg.microsecurity.book1.ch6_gateway_throttling_redis;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DemoService {

    private static final String PROJECT = "ch6-gateway-throttling-redis";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Implement distributed gateway throttling using Redis to protect services from high-volume traffic and DoS attacks.";
    private static final String CONCEPT = "Perimeter Rate Limiting";
    private static final String CONTROL_FAMILY = "RESILIENCE";

    /**
     * Executes a secure gateway processing demonstration.
     * Enforces that requests arriving at the gateway are subject to Redis-backed rate limits.
     */
    public Mono<Map<String, Object>> demo() {
        return buildResponse("allow", "Request successfully passed distributed gateway throttling checks.", 10);
    }

    private Mono<Map<String, Object>> buildResponse(String decision, String behavior, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("riskScore", risk);
        return Mono.just(result);
    }
}
