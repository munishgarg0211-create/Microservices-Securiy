package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PolicyEngine {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Rule> rules = new ArrayList<>();

    @PostConstruct
    public void init() {
        loadRules();
    }

    public void loadRules() {
        try (InputStream is = getClass().getResourceAsStream("/policy.json")) {
            JsonNode root = objectMapper.readTree(is);
            JsonNode rulesNode = root.get("rules");
            rules.clear();
            if (rulesNode.isArray()) {
                for (JsonNode node : rulesNode) {
                    rules.add(new Rule(
                        node.get("id").asText(),
                        node.get("field").asText(),
                        node.get("operator").asText(),
                        node.get("value").asText(),
                        node.get("riskImpact").asInt()
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load policies", e);
        }
    }

    public PolicyResult evaluate(Map<String, String> params) {
        int totalRisk = 10; // Base risk for any processed request
        List<String> violations = new ArrayList<>();

        for (Rule rule : rules) {
            String actualValue = params.getOrDefault(rule.field, "false");
            boolean match = "EQUALS".equals(rule.operator) && actualValue.equals(rule.value);
            
            if (!match) {
                totalRisk += rule.riskImpact;
                violations.add(rule.id);
            }
        }

        return new PolicyResult(violations.isEmpty(), totalRisk, violations);
    }

    public record Rule(String id, String field, String operator, String value, int riskImpact) {}
    public record PolicyResult(boolean allowed, int riskScore, List<String> violations) {}
}
