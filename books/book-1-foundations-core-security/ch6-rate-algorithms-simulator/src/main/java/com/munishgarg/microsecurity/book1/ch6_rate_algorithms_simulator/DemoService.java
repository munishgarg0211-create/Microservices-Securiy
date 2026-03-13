package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final TokenBucket tokenBucket = new TokenBucket(10, 5); // 10 burst, 5/sec
    private final LeakyBucket leakyBucket = new LeakyBucket(10, 5); // 10 capacity, 5/sec

    private static final String PROJECT = "ch6-rate-algorithms-simulator";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Simulate and compare different rate limiting algorithms (Token Bucket, Leaky Bucket) for microservice defense.";
    private static final String CONCEPT = "Rate Limiting Algorithms";
    private static final String CONTROL_FAMILY = "RESILIENCE";

    /**
     * Executes a rate limiting simulation.
     * Compares the behavior of Token Bucket vs Leaky Bucket algorithms.
     */
    public Map<String, Object> demo(String algorithm) {
        boolean allowed;
        String algoName;

        if ("leaky".equalsIgnoreCase(algorithm)) {
            allowed = leakyBucket.tryConsume();
            algoName = "Leaky Bucket";
        } else {
            allowed = tokenBucket.tryConsume();
            algoName = "Token Bucket";
        }

        return buildResponse(
            allowed ? "allow" : "throttle", 
            allowed ? algoName + " passed request under current capacity." : algoName + " capacity exceeded - request throttled.", 
            allowed ? 15 : 88);
    }

    private Map<String, Object> buildResponse(String decision, String behavior, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("riskScore", risk);
        return result;
    }
}
