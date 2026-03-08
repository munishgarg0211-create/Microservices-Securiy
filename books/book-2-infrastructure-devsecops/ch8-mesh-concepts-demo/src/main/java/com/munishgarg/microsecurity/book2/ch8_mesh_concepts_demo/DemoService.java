package com.munishgarg.microsecurity.book2.ch8_mesh_concepts_demo;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch8-mesh-concepts-demo",
                "book", "book-2-infrastructure-devsecops",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
