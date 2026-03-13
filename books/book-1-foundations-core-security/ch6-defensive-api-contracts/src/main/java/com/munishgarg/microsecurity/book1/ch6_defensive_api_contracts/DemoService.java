package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    /**
     * SECURE MODE: Enforces deadline checks (Fail-Fast).
     */
    public Map<String, Object> processSecure(long workDurationMs) {
        if (DeadlineContext.isExpired()) {
            return buildResponse("secure", "block", "Deadline exceeded! Failing fast to save resources.", null, 10);
        }

        simulateWork(workDurationMs);

        if (DeadlineContext.isExpired()) {
             return buildResponse("secure", "block", "Deadline exceeded during processing! Abandoning results.", null, 20);
        }

        return buildResponse("secure", "allow", "Processing completed within deadline budget.", "Success Data", 15);
    }

    /**
     * INSECURE MODE: Ignores deadlines.
     */
    public Map<String, Object> processInsecure(long workDurationMs) {
        simulateWork(workDurationMs);
        return buildResponse("insecure", "allow", "Processing completed ignoring any deadlines.", "Success Data", 85);
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

    private Map<String, Object> buildResponse(String mode, String decision, String behavior, Object data, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", "ch6-defensive-api-contracts");
        result.put("mode", mode);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("data", data);
        result.put("riskScore", risk);
        return result;
    }
}
