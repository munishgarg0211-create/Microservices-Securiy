package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch4-token-validation-strategies",
                "book", "book-1-foundations-core-security",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
