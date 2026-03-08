package com.munishgarg.microsecurity.book2.ch7_k8s_rbac_isolation;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch7-k8s-rbac-isolation",
                "book", "book-2-infrastructure-devsecops",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
