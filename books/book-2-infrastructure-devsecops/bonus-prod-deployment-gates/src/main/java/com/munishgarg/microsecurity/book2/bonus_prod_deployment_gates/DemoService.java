package com.munishgarg.microsecurity.book2.bonus_prod_deployment_gates;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "bonus-prod-deployment-gates",
                "book", "book-2-infrastructure-devsecops",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
