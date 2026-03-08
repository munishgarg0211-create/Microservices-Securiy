package com.munishgarg.microsecurity.book1.ch5_gateway_role_demo;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch5-gateway-role-demo",
                "book", "book-1-foundations-core-security",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
