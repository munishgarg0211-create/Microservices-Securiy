package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final DownstreamService downstreamService;

    public DemoService(DownstreamService downstreamService) {
        this.downstreamService = downstreamService;
    }

    /**
     * SECURE MODE: Enforces Circuit Breaker and Bulkhead patterns.
     * Use Resilience4j annotations to wrap the unstable downstream call.
     */
    @CircuitBreaker(name = "downstreamService", fallbackMethod = "fallback")
    @Bulkhead(name = "downstreamService", fallbackMethod = "fallback")
    public Map<String, Object> demoSecure(boolean shouldFail, long delayMs) {
        String result = downstreamService.call(shouldFail, delayMs);
        return buildResponse("secure", "allow", "Downstream call succeeded under protection", result, 20);
    }

    /**
     * INSECURE MODE: Bypasses resilience patterns.
     * Call the service directly without any guards.
     */
    public Map<String, Object> demoInsecure(boolean shouldFail, long delayMs) {
        try {
            String result = downstreamService.call(shouldFail, delayMs);
            return buildResponse("insecure", "allow", "Downstream call succeeded (unprotected)", result, 50);
        } catch (Exception e) {
            // In insecure mode, the caller is directly impacted by downstream failure
            return buildResponse("insecure", "error", "Downstream call failed and impacted the system", e.getMessage(), 95);
        }
    }

    /**
     * Resilience Fallback Method.
     * Triggered when circuit is OPEN, bulkhead is FULL, or downstream fails.
     */
    public Map<String, Object> fallback(boolean shouldFail, long delayMs, Throwable t) {
        return buildResponse("secure", "fallback", 
            "Resilience fallback triggered. System stays responsive despite downstream failure: " + t.getMessage(), 
            "Cached/Static Data", 10);
    }

    private Map<String, Object> buildResponse(String mode, String decision, String behavior, Object data, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", "ch6-circuit-breaker-security");
        result.put("mode", mode);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("data", data);
        result.put("riskScore", risk);
        return result;
    }
}
