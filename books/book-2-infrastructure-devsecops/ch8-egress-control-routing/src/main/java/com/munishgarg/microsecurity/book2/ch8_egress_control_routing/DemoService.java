package com.munishgarg.microsecurity.book2.ch8_egress_control_routing;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch8-egress-control-routing",
                "book", "book-2-infrastructure-devsecops",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
