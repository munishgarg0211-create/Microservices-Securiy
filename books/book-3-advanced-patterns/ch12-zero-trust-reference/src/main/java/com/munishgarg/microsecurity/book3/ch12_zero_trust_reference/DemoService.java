package com.munishgarg.microsecurity.book3.ch12_zero_trust_reference;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch12-zero-trust-reference",
                "book", "book-3-advanced-patterns",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
