package com.munishgarg.microsecurity.book1.ch1_sdlc_security_pipeline;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch1-sdlc-security-pipeline",
                "book", "book-1-foundations-core-security",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
