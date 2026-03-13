package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final DownstreamService downstreamService;

    private static final String PROJECT = "ch6-circuit-breaker-security";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Implement Circuit Breaker and Bulkhead patterns to prevent cascading failures in microservices.";
    private static final String CONCEPT = "Service Resilience";
    private static final String CONTROL_FAMILY = "RESILIENCE";

    public DemoService(DownstreamService downstreamService) {
        this.downstreamService = downstreamService;
    }

    /**
     * Executes a resilient downstream call demonstration.
     * Use Resilience4j annotations to wrap the unstable downstream call.
     */
    @CircuitBreaker(name = "downstreamService", fallbackMethod = "fallback")
    @Bulkhead(name = "downstreamService", fallbackMethod = "fallback")
    public Map<String, Object> demo(boolean shouldFail, long delayMs) {
        String result = downstreamService.call(shouldFail, delayMs);
        return buildResponse("allow", "Downstream call succeeded under protection", result, 15);
    }

    /**
     * Resilience Fallback Method.
     * Triggered when circuit is OPEN, bulkhead is FULL, or downstream fails.
     */
    public Map<String, Object> fallback(boolean shouldFail, long delayMs, Throwable t) {
        return buildResponse("fallback", 
            "Resilience fallback triggered. System stays responsive despite downstream failure: " + t.getMessage(), 
            "Cached/Static Data", 10);
    }

    private Map<String, Object> buildResponse(String decision, String behavior, Object data, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("data", data);
        result.put("riskScore", risk);
        return result;
    }
}
