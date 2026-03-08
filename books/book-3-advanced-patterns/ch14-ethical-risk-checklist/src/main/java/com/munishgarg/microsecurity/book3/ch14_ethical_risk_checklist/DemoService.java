package com.munishgarg.microsecurity.book3.ch14_ethical_risk_checklist;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch14-ethical-risk-checklist",
                "book", "book-3-advanced-patterns",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
