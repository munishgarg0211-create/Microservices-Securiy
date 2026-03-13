package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch6-defensive-api-contracts";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Implement defensive API contracts using deadline propagation to prevent resource exhaustion.";
    private static final String CONCEPT = "Defensive Programming";
    private static final String CONTROL_FAMILY = "RESILIENCE";

    /**
     * Executes a secure processing demonstration with deadline enforcement.
     */
    public Map<String, Object> demo(long workDurationMs) {
        if (DeadlineContext.isExpired()) {
            return buildResponse("block", "Deadline exceeded! Failing fast to save resources before starting work.", null, 10);
        }

        simulateWork(workDurationMs);

        if (DeadlineContext.isExpired()) {
             return buildResponse("block", "Deadline exceeded during processing! Abandoning results to prevent further cascading delay.", null, 20);
        }

        return buildResponse("allow", "Processing completed successfully within the allocated deadline budget.", "Success Data", 15);
    }

    private void simulateWork(long duration) {
        if (duration > 0) {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
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
